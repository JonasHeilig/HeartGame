package de.jonasheilig.heartGame.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class ItemTabCompleter : TabCompleter {

    private val itemNames = listOf(
        "heart_emerald",
    )

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        if (args.size == 1) {
            return itemNames.filter { it.startsWith(args[0].lowercase()) }
        }
        return emptyList()
    }
}
