package de.jonasheilig.heartGame.mode

import net.kyori.adventure.text.Component
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin

class SurvivalSpectatorMode(private val plugin: Plugin) : Listener {

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity as? Player ?: return

        if (player.health <= 0.0) {
            enterSurvivalSpectatorMode(player)
        }
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        if (isInSpectatorMode(player)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        if (isInSpectatorMode(player)) {
            savePlayerMode(player, false)
        }
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (isInSpectatorMode(player)) {
            enterSurvivalSpectatorMode(player)
        }
    }

    fun enterSurvivalSpectatorMode(player: Player) {
        player.gameMode = GameMode.SURVIVAL
        player.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, Int.MAX_VALUE, 0, false, false))
        player.isFlying = true
        player.sendMessage("You have entered Survival Spectator mode because you have no hearts left.")

        giveSpectatorCompass(player)
        savePlayerMode(player, true)
    }

    fun toggleSurvivalSpectatorMode(player: Player) {
        if (isInSpectatorMode(player)) {
            player.gameMode = GameMode.SURVIVAL
            player.removePotionEffect(PotionEffectType.INVISIBILITY)
            player.isFlying = false

            val config = plugin.config
            val savedHearts = config.getDouble("players.${player.uniqueId}.hearts", 20.0)
            player.health = savedHearts
            player.sendMessage("You have exited Survival Spectator mode.")

            savePlayerMode(player, false)
        } else {
            player.sendMessage("You are not in Survival Spectator mode.")
        }
    }

    private fun giveSpectatorCompass(player: Player) {
        val compass = ItemStack(Material.COMPASS)
        val meta = compass.itemMeta
        meta?.displayName(Component.text("Teleport Compass"))
        compass.itemMeta = meta
        player.inventory.addItem(compass)
    }

    private fun savePlayerMode(player: Player, inSpectatorMode: Boolean) {
        val config = plugin.config
        config.set("players.${player.uniqueId}.inSpectatorMode", inSpectatorMode)
        plugin.saveConfig()
    }

    private fun isInSpectatorMode(player: Player): Boolean {
        return plugin.config.getBoolean("players.${player.uniqueId}.inSpectatorMode", false)
    }
}
