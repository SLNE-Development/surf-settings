package dev.slne.surf.settings.core.client.service

import dev.slne.surf.settings.api.setting.PlayerSetting
import dev.slne.surf.settings.api.setting.Setting
import dev.slne.surf.settings.api.setting.SettingKey
import net.kyori.adventure.key.Key
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

/**
 * Covers the in-memory contract of [SettingServiceImpl].
 *
 * Paths that issue RabbitMQ requests (`refreshSettings`, `createSetting`, `cachePlayerSettings`,
 * `savePlayerSetting`) are not exercised: they resolve `ClientSettingsInstance` through
 * `requiredService`, which needs a platform module on the classpath. They are the only way to seed
 * the registry, which is why the assertions below focus on lifecycle and publication semantics
 * rather than populated lookups.
 */
class SettingServiceImplTest {

    private val setting = Setting(name = "chat:pings", defaultValue = "true")

    @Test
    fun `settingKey name is computed once`() {
        val key = SettingKey.ofBoolean(Key.key("chat", "pings"), defaultValue = true)

        assertEquals("chat:pings", key.name)
        // Key.asString() concatenates on every call; the hot read path depends on this being a
        // stable instance so the String is neither reallocated nor re-hashed per lookup.
        assertSame(key.name, key.name)
    }

    @Test
    fun `caching a setting does not resurrect an untracked player`() {
        val service = SettingServiceImpl()
        val playerUuid = UUID.randomUUID()

        service.cachePlayerSetting(playerUuid, PlayerSetting(setting, "false"))

        // A save completing after the player disconnected must not recreate their cache entry,
        // which nothing would ever remove again.
        assertTrue(service.getSettingsForPlayer(playerUuid).isEmpty())
        assertNull(service.getSettingForPlayer(playerUuid, setting.name))
    }

    @Test
    fun `invalidate leaves no trace for a player`() {
        val service = SettingServiceImpl()
        val playerUuid = UUID.randomUUID()

        service.invalidatePlayerSettingsCache(playerUuid)

        assertTrue(service.getSettingsForPlayer(playerUuid).isEmpty())
        assertTrue(service.playerSettings.isEmpty())
    }

    @Test
    fun `unknown setting reads as null on every read path`() {
        val service = SettingServiceImpl()
        val playerUuid = UUID.randomUUID()

        assertNull(service.getSettingByName("does:not-exist"))
        assertNull(service.getSettingForPlayerOrDefault(playerUuid, "does:not-exist"))
        assertNull(service.getSettingValueOrDefault(playerUuid, "does:not-exist"))
    }

    @Test
    fun `fast value read agrees with the PlayerSetting read`() {
        val service = SettingServiceImpl()
        val playerUuid = UUID.randomUUID()

        for (name in listOf("chat:pings", "friend:sounds", "does:not-exist")) {
            assertEquals(
                service.getSettingForPlayerOrDefault(playerUuid, name)?.settingValue,
                service.getSettingValueOrDefault(playerUuid, name),
                "getSettingValueOrDefault must match getSettingForPlayerOrDefault for $name"
            )
        }
    }

    @Test
    fun `published snapshots are not writable by callers`() {
        val service = SettingServiceImpl()
        val playerUuid = UUID.randomUUID()

        // Both are snapshots handed out to arbitrary callers; mutating them used to corrupt the
        // live cache instead of failing.
        assertThrows<UnsupportedOperationException> { service.settings.add(setting) }
        assertThrows<UnsupportedOperationException> {
            service.getSettingsForPlayer(playerUuid).add(PlayerSetting(setting, "false"))
        }
    }

    @RepeatedTest(3)
    fun `concurrent reads writes and invalidation stay consistent`() {
        val service = SettingServiceImpl()
        val players = List(16) { UUID.randomUUID() }
        val threads = 8
        val iterations = 2_000

        val start = CountDownLatch(1)
        val done = CountDownLatch(threads)
        val failure = AtomicReference<Throwable>()

        repeat(threads) { threadIndex ->
            Thread.ofPlatform().name("settings-stress-$threadIndex").start {
                try {
                    start.await()
                    repeat(iterations) { i ->
                        val playerUuid = players[(threadIndex + i) % players.size]
                        when (i % 5) {
                            0 -> service.cachePlayerSetting(
                                playerUuid,
                                PlayerSetting(setting, i.toString())
                            )

                            1 -> service.getSettingValueOrDefault(playerUuid, setting.name)
                            2 -> service.getSettingsForPlayer(playerUuid).size
                            3 -> service.playerSettings.size
                            else -> service.invalidatePlayerSettingsCache(playerUuid)
                        }
                    }
                } catch (throwable: Throwable) {
                    failure.compareAndSet(null, throwable)
                } finally {
                    done.countDown()
                }
            }
        }

        start.countDown()
        assertTrue(done.await(60, TimeUnit.SECONDS), "stress threads did not finish")
        failure.get()?.let { throw AssertionError("concurrent access failed", it) }

        // Nothing was ever loaded for these players, so no write may have created an entry.
        assertTrue(service.playerSettings.isEmpty())
        assertFalse(players.any { service.getSettingsForPlayer(it).isNotEmpty() })
    }
}
