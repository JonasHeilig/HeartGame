package de.jonasheilig.heartGame.listeners

import de.jonasheilig.heartGame.HeartGame
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener(private val plugin: HeartGame) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val config = plugin.config

        val hearts = config.getDouble("players.${player.uniqueId}.hearts", 20.0)

        player.health = hearts

        if (player.health > hearts) {
            player.health = hearts
        }

        player.sendMessage("Welcome back! Your maximum health has been adjusted to ${player.health / 2} hearts.")
    }
}
