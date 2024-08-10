package de.jonasheilig.heartGame.commands

import de.jonasheilig.heartGame.utils.LocationUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RespawnCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val currentLocation = sender.location
            val respawnLocation = LocationUtils.getRandomLocationNearby(currentLocation, 20)

            sender.teleport(respawnLocation)
            sender.sendMessage("You have been respawned to a random location nearby!")
        } else {
            sender.sendMessage("This command can only be run by a player.")
        }
        return true
    }
}
