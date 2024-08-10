package de.jonasheilig.heartGame.items

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import de.jonasheilig.heartGame.HeartGame
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class ReviveBeacon {

    companion object {
        fun create(): ItemStack {
            val item = ItemStack(Material.BEACON, 1)
            val meta = item.itemMeta

            meta?.displayName(Component.text("Revive Beacon").color(NamedTextColor.RED))
            meta?.lore(listOf(Component.text("Place on a 3x3 diamond block frame to activate.").color(NamedTextColor.GRAY)))

            val key = NamespacedKey(HeartGame.instance, "revive_beacon")
            meta?.persistentDataContainer?.set(key, PersistentDataType.STRING, "revive_beacon")
            item.itemMeta = meta
            return item
        }

        fun isReviveBeacon(item: ItemStack): Boolean {
            val meta = item.itemMeta ?: return false
            val key = NamespacedKey(HeartGame.instance, "revive_beacon")
            return meta.persistentDataContainer.has(key, PersistentDataType.STRING)
        }
    }
}
