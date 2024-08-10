package de.jonasheilig.heartGame.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class GiveHeartsCommand(private val plugin: Plugin) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender.hasPermission("heartgame.givehearts")) {
            if (args.size != 2) {
                sender.sendMessage("Usage: /givehearts <player> <amount>")
                return true
            }

            val targetPlayer: Player? = Bukkit.getPlayer(args[0])
            val heartsAmount: Double = args[1].toDoubleOrNull()?.times(2) ?: run {
                sender.sendMessage("Please provide a valid number of hearts.")
                return true
            }

            if (targetPlayer == null) {
                sender.sendMessage("Player not found.")
                return true
            }

            val newHealth = (targetPlayer.health + heartsAmount).coerceIn(2.0, 20.0)
            targetPlayer.health = newHealth
            targetPlayer.health = newHealth
            sender.sendMessage("You gave ${args[1]} hearts to ${targetPlayer.name}. They now have ${newHealth / 2} hearts.")
            targetPlayer.sendMessage("You received ${args[1]} hearts. Your new max health is ${newHealth / 2} hearts.")

            val config = plugin.config
            config.set("players.${targetPlayer.uniqueId}.hearts", targetPlayer.health)
            plugin.saveConfig()
        } else {
            sender.sendMessage("You do not have permission to use this command.")
        }
        return true
    }
}
