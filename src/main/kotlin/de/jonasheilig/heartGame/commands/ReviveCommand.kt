package de.jonasheilig.heartGame.commands

import de.jonasheilig.heartGame.HeartGame
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.GameMode

class ReviveCommand(private val plugin: HeartGame) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender.hasPermission("heartgame.revive")) {
            if (args.isNotEmpty()) {
                val targetName = args[0]
                val targetPlayer = Bukkit.getPlayer(targetName)

                if (targetPlayer != null) {
                    revivePlayer(targetPlayer)
                    sender.sendMessage("Player $targetName has been revived.")
                } else {
                    sender.sendMessage("Player $targetName not found.")
                }
            } else {
                sender.sendMessage("Please specify a player to revive.")
            }
        } else {
            sender.sendMessage("You don't have permission to use this command.")
        }
        return true
    }

    private fun revivePlayer(player: Player) {
        player.gameMode = GameMode.SURVIVAL
        player.health = 20.0
        player.foodLevel = 20
        player.inventory.clear()

        player.activePotionEffects.forEach { player.removePotionEffect(it.type) }

        player.sendMessage("You have been revived and set to full health with an empty inventory.")
    }
}
