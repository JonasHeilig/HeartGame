package de.jonasheilig.heartGame.listeners

import de.jonasheilig.heartGame.utils.LocationUtils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.plugin.Plugin

class PlayerDeathListener(private val plugin: Plugin) : Listener {

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity
        val deathLocation = player.location
        val respawnLocation = LocationUtils.getRandomLocationNearby(deathLocation, 20)

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            player.spigot().respawn()
            player.teleport(respawnLocation)
        }, 40L)
    }
}
