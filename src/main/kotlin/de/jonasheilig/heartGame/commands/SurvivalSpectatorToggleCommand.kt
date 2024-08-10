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
            survivalSpectatorMode.toggleSurvivalSpectatorMode(player)
        } else {
            sender.sendMessage("§cOnly players can use this command.")
        }
        return true
    }
}
