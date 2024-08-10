package de.jonasheilig.heartGame.listeners

import de.jonasheilig.heartGame.items.HeartEmerald
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.Plugin


class HeartEmeraldListener(private val plugin: Plugin) : Listener {

    @EventHandler
    fun onPlayerUseHeartEmerald(event: PlayerInteractEvent) {
        val player = event.player
        val itemInHand = event.item

        if (itemInHand != null && HeartEmerald.isHeartEmerald(itemInHand)) {
            player.health += 2.0
            player.health = player.health
            player.sendMessage("You have gained an extra heart! Your new maximum health is ${player.health / 2} hearts.")
            val config = plugin.config
            config.set("players.${player.uniqueId}.hearts", player.health)
            plugin.saveConfig()

            itemInHand.amount -= 1
        }
    }
}
