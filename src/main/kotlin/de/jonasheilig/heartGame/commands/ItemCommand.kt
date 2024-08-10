package de.jonasheilig.heartGame.commands

import de.jonasheilig.heartGame.HeartGame
import de.jonasheilig.heartGame.items.HeartEmerald
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ItemCommand(private val plugin: HeartGame) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (args.isNotEmpty()) {
                val itemName = args[0].lowercase()
                val item = when (itemName) {
                    "heart_emerald" -> HeartEmerald.create()
                    else -> {
                        sender.sendMessage("Invalid item name: $itemName")
                        return true
                    }
                }
                val player = sender as Player
                player.inventory.addItem(item)
                player.sendMessage("You have been given a ${item.type.name}.")
            } else {
                sender.sendMessage("Please specify an item name.")
            }
        } else {
            sender.sendMessage("Only players can use this command.")
        }
        return true
    }
}
