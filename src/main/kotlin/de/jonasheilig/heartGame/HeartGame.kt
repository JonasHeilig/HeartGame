package de.jonasheilig.heartGame

import org.bukkit.plugin.java.JavaPlugin
import de.jonasheilig.heartGame.listeners.*
import de.jonasheilig.heartGame.commands.*

class HeartGame : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        logger.info("HeartGame Plugin has been enabled")

        // Register listeners
        server.pluginManager.registerEvents(PlayerDeathListener(), this)

        // Register commands
        getCommand("respawn")?.setExecutor(RespawnCommand())
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
