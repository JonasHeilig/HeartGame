package de.jonasheilig.heartGame.listeners

import de.jonasheilig.heartGame.mode.SurvivalSpectatorMode
import de.jonasheilig.heartGame.utils.LocationUtils
import org.bukkit.Bukkit
import org.bukkit.attribute.Attribute
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.plugin.Plugin
import org.bukkit.entity.Player

class PlayerDeathListener(private val plugin: Plugin) : Listener {

    private val survivalSpectatorMode = SurvivalSpectatorMode(plugin)

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity as? Player ?: return
        val killer = event.entity.killer

        if (killer is Player) {
            val maxHealth = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: 20.0
            val killerNewHealth = (killer.health + 2.0).coerceAtMost(maxHealth)
            killer.health = killerNewHealth
            killer.sendMessage("You gained a heart! Your new health is ${killerNewHealth / 2} hearts.")

            val config = plugin.config
            config.set("players.${killer.uniqueId}.hearts", killerNewHealth)
            plugin.saveConfig()
        }

        val playerNewHealth = (player.health - 2.0).coerceAtLeast(0.0)
        if (playerNewHealth > 0) {
            player.health = playerNewHealth
            player.sendMessage("You lost a heart! Your new health is ${playerNewHealth / 2} hearts.")

            val config = plugin.config
            config.set("players.${player.uniqueId}.hearts", playerNewHealth)
            plugin.saveConfig()
        } else {
            survivalSpectatorMode.enterSurvivalSpectatorMode(player)
        }

        val deathLocation = player.location
        val respawnLocation = LocationUtils.getRandomLocationNearby(deathLocation, 20)

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
            player.spigot().respawn()
            player.teleport(respawnLocation)
        }, 40L)
    }
}
