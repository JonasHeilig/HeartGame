package de.jonasheilig.heartGame.ui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import de.jonasheilig.heartGame.mode.SurvivalSpectatorMode
import org.bukkit.plugin.Plugin

fun showReviveUI(player: Player, plugin: Plugin) {
    val inventory = Bukkit.createInventory(null, 54, Component.text("Select a Player to Revive").color(NamedTextColor.GREEN))

    val spectatorMode = SurvivalSpectatorMode(plugin)
    Bukkit.getOnlinePlayers().forEach { p ->
        if (spectatorMode.isInSpectatorMode(p)) {
            val head = ItemStack(Material.PLAYER_HEAD)
            val meta = head.itemMeta
            meta?.displayName(Component.text(p.name).color(NamedTextColor.YELLOW))
            head.itemMeta = meta
            inventory.addItem(head)
        }
    }

    player.openInventory(inventory)
}
