package de.jonasheilig.heartGame.listeners

import de.jonasheilig.heartGame.items.EmptyHeartContainer
import de.jonasheilig.heartGame.items.HeartEmerald
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.Plugin

class EmptyHeartContainerListener(private val plugin: Plugin) : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?: return

        if (event.action.name != "RIGHT_CLICK_AIR" && event.action.name != "RIGHT_CLICK_BLOCK") return
        if (!EmptyHeartContainer.isHeartContainer(item)) return

        if (player.health > 1) {
            player.health -= 2.0
            player.inventory.addItem(HeartEmerald.create())
            player.sendMessage("You have lost a heart and received a Heart Emerald.")

            val itemStack = player.inventory.first { EmptyHeartContainer.isHeartContainer(it) }
            player.inventory.remove(itemStack)
        } else {
            player.sendMessage("You don't have enough health to use this item.")
        }

        event.isCancelled = true
    }
}
