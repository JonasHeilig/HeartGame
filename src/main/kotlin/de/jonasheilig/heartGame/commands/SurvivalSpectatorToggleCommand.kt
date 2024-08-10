package de.jonasheilig.heartGame.commands

import de.jonasheilig.heartGame.HeartGame
import de.jonasheilig.heartGame.mode.SurvivalSpectatorMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SurvivalSpectatorToggleCommand(private val plugin: HeartGame) : CommandExecutor {

    private val survivalSpectatorMode = SurvivalSpectatorMode(plugin)

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val player = sender as Player
            if (player.hasPermission("heartgame.survivalspectatortoggle")) {
                survivalSpectatorMode.toggleSurvivalSpectatorMode(player)
            } else {
                player.sendMessage("You do not have permission to use this command.")
            }
        } else {
            sender.sendMessage("Only players can use this command.")
        }
        return true
    }
}
