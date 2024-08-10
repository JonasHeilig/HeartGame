package de.jonasheilig.heartGame

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.configuration.file.FileConfiguration
import de.jonasheilig.heartGame.listeners.*
import de.jonasheilig.heartGame.commands.*

class HeartGame : JavaPlugin() {

    companion object {
        lateinit var instance: HeartGame
            private set
    }

    override fun onEnable() {
        // Plugin startup logic
        logger.info("HeartGame Plugin has been enabled")

        // Register listeners
        server.pluginManager.registerEvents(PlayerDeathListener(this), this)
        server.pluginManager.registerEvents(PlayerJoinListener(this), this)

        // Register commands
        getCommand("respawn")?.setExecutor(RespawnCommand())
        getCommand("givehearts")?.setExecutor(GiveHeartsCommand(this))
        getCommand("togglemode")?.setExecutor(SurvivalSpectatorToggleCommand(this))

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
