package de.jonasheilig.heartGame.listeners

import de.jonasheilig.heartGame.utils.LocationUtils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.plugin.Plugin
import org.bukkit.entity.Player

class PlayerDeathListener(private val plugin: Plugin) : Listener {

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity
        val killer = event.entity.killer

        if (killer is Player) {
            if (player.health > 2.0) {
                player.health -= 2.0
                player.sendMessage("You lost a heart! Your new max health is ${player.health / 2} hearts.")

                val config = plugin.config
                config.set("players.${player.uniqueId}.hearts", player.health)
                plugin.saveConfig()
            }

            killer.health += 2.0
            killer.health = killer.health
            killer.sendMessage("You gained a heart! Your new max health is ${killer.health / 2} hearts.")

            val config = plugin.config
            config.set("players.${killer.uniqueId}.hearts", killer.health)
            plugin.saveConfig()
        }

        val deathLocation = player.location
        val respawnLocation = LocationUtils.getRandomLocationNearby(deathLocation, 20)

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            player.spigot().respawn()
            player.teleport(respawnLocation)
        }, 40L)
    }
}
