package de.jonasheilig.heartGame.mode

import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin

class SurvivalSpectatorMode(private val plugin: Plugin) : Listener {

    private val modePlayers = mutableSetOf<Player>()

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity as? Player ?: return

        if (player.health <= 0.0 && !modePlayers.contains(player)) {
            enterSurvivalSpectatorMode(player)
        }
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        if (modePlayers.contains(player)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        if (modePlayers.contains(player)) {
            modePlayers.remove(player)
        }
    }

    fun enterSurvivalSpectatorMode(player: Player) {
        player.gameMode = GameMode.SURVIVAL
        player.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, Int.MAX_VALUE, 0, false, false))
        player.isFlying = true
        player.sendMessage("You have entered Survival Spectator mode because you have no hearts left.")

        val config = plugin.config
        config.set("players.${player.uniqueId}.hearts", player.health)
        plugin.saveConfig()

        modePlayers.add(player)
    }

    fun toggleSurvivalSpectatorMode(player: Player) {
        if (modePlayers.contains(player)) {
            player.gameMode = GameMode.SURVIVAL
            player.removePotionEffect(PotionEffectType.INVISIBILITY)
            player.isFlying = false

            val config = plugin.config
            val savedHearts = config.getDouble("players.${player.uniqueId}.hearts", 20.0)
            player.health = savedHearts
            player.sendMessage("You have exited Survival Spectator mode.")

            modePlayers.remove(player)
        } else {
            player.sendMessage("You are not in Survival Spectator mode.")
        }
    }
}
