package de.jonasheilig.heartGame.listeners

import de.jonasheilig.heartGame.items.ReviveBeacon
import de.jonasheilig.heartGame.mode.SurvivalSpectatorMode
import de.jonasheilig.heartGame.ui.showReviveUI
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

class ReviveBeaconListener(private val plugin: Plugin) : Listener {

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        val block = event.block
        val item = event.itemInHand

        if (ReviveBeacon.isReviveBeacon(item)) {
            val blockType = block.type
            if (blockType == Material.BEACON && checkDiamondFrameAround(block)) {
                startReviveAnimation(block.location)

                showReviveUI(player, plugin)

                object : BukkitRunnable() {
                    override fun run() {
                        executeRevive(block.location, player)
                    }
                }.runTaskLater(plugin, 20 * 20)
            }
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        val clickedItem = event.currentItem ?: return

        if (event.view.title() == Component.text("Select a Player to Revive").color(NamedTextColor.GREEN)) {
            event.isCancelled = true

            if (clickedItem.type == Material.PLAYER_HEAD) {
                val meta = clickedItem.itemMeta as SkullMeta
                val targetComponent = meta.displayName() ?: return
                val targetName = LegacyComponentSerializer.legacySection().serialize(targetComponent)
                val target = Bukkit.getPlayer(targetName)

                if (target != null && SurvivalSpectatorMode(plugin).isInSpectatorMode(target)) {
                    revivePlayer(target, player)
                    player.sendMessage("${target.name} wurde erfolgreich wiederbelebt!")
                    player.closeInventory()
                }
            }
        }
    }

    private fun revivePlayer(target: Player, reviver: Player) {
        val location = reviver.location
        location.block.type = Material.AIR
        for (x in -1..1) {
            for (y in -1..1) {
                for (z in -1..1) {
                    val block = location.clone().add(x.toDouble(), y.toDouble(), z.toDouble()).block
                    if (block.type == Material.DIAMOND_BLOCK) {
                        block.type = Material.AIR
                    }
                }
            }
        }

        val spectatorMode = SurvivalSpectatorMode(plugin)
        spectatorMode.toggleSurvivalSpectatorMode(target)
        target.gameMode = GameMode.SURVIVAL
        target.health = 20.0
        target.sendMessage("Du wurdest von ${reviver.name} wiederbelebt!")
    }

    private fun checkDiamondFrameAround(centerBlock: Block): Boolean {
        val directions = arrayOf(
            Vector(1, 0, 0), Vector(-1, 0, 0),
            Vector(0, 0, 1), Vector(0, 0, -1),
            Vector(1, 0, 1), Vector(-1, 0, -1),
            Vector(-1, 0, 1), Vector(1, 0, -1)
        )
        return directions.all {
            val block = centerBlock.getRelative(it.blockX, it.blockY, it.blockZ)
            block.type == Material.DIAMOND_BLOCK
        }
    }

    private fun startReviveAnimation(location: Location) {
        object : BukkitRunnable() {
            override fun run() {
                val radius = 5.0
                val center = location.clone().add(0.5, 1.0, 0.5)
                for (angle in 0..360 step 15) {
                    val radians = Math.toRadians(angle.toDouble())
                    val x = radius * Math.cos(radians)
                    val z = radius * Math.sin(radians)
                    val particleLocation = center.clone().add(x, 0.0, z)
                    location.world?.spawnParticle(Particle.END_ROD, particleLocation, 1)
                }
            }
        }.runTaskTimer(plugin, 0L, 5L)
    }

    private fun executeRevive(location: Location, player: Player) {
        val world = location.world
        world?.let {
            location.block.type = Material.AIR
            for (x in -1..1) {
                for (y in -1..1) {
                    for (z in -1..1) {
                        val block = location.clone().add(x.toDouble(), y.toDouble(), z.toDouble()).block
                        if (block.type == Material.DIAMOND_BLOCK) {
                            block.type = Material.AIR
                        }
                    }
                }
            }
        }
    }
}
