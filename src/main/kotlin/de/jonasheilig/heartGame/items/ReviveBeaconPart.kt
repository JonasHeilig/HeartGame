package de.jonasheilig.heartGame.items

import de.jonasheilig.heartGame.HeartGame
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class ReviveBeaconPart {
        companion object {
        fun create(): ItemStack {
            val item = ItemStack(Material.STONE, 1)
            val meta = item.itemMeta

            meta?.displayName(Component.text("Revive Beacon Part").color(NamedTextColor.RED))
            meta?.lore(listOf(Component.text("You need this part to craft the Revive Beacon").color(NamedTextColor.GRAY)))

            val key = NamespacedKey(HeartGame.instance, "revive_beacon_part")
            meta?.persistentDataContainer?.set(key, PersistentDataType.STRING, "revive_beacon_part")
            item.itemMeta = meta
            return item
        }

        fun isReviveBeacon(item: ItemStack): Boolean {
            val meta = item.itemMeta ?: return false
            val key = NamespacedKey(HeartGame.instance, "revive_beacon_part")
            return meta.persistentDataContainer.has(key, PersistentDataType.STRING)
        }
    }
}