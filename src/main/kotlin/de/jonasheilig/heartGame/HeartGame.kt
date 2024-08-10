package de.jonasheilig.heartGame

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import de.jonasheilig.heartGame.listeners.*
import de.jonasheilig.heartGame.commands.*

class HeartGame : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        logger.info("HeartGame Plugin has been enabled")

        // Register listeners
        server.pluginManager.registerEvents(PlayerDeathListener(this), this)

        // Register commands
        getCommand("respawn")?.setExecutor(RespawnCommand())
        getCommand("givehearts")?.setExecutor(GiveHeartsCommand(this))

        loadPlayerHearts()
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("HeartGame Plugin has been disabled")

        savePlayerHearts()
    }

    private fun loadPlayerHearts() {
        val config: FileConfiguration = config
        for (player in server.onlinePlayers) {
            val hearts = config.getDouble("players.${player.uniqueId}.hearts", 20.0)
            player.health = hearts
        }
    }

    private fun savePlayerHearts() {
        val config: FileConfiguration = config
        for (player in server.onlinePlayers) {
            config.set("players.${player.uniqueId}.hearts", player.health)
        }
        saveConfig()
    }
}
