package de.jonasheilig.heartGame.items

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import de.jonasheilig.heartGame.HeartGame
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class HeartEmerald {

    companion object {
        fun create(): ItemStack {
            val item = ItemStack(Material.EMERALD, 1)
            val meta = item.itemMeta

            meta?.displayName(Component.text("Heart Emerald").color(NamedTextColor.GREEN))
            meta?.lore(listOf(Component.text("Right-click to gain an extra heart.").color(NamedTextColor.GRAY)))

            val key = NamespacedKey(HeartGame.instance, "heart_emerald")
            meta?.persistentDataContainer?.set(key, PersistentDataType.STRING, "heart_emerald")
            item.itemMeta = meta
            return item
        }

        fun isHeartEmerald(item: ItemStack): Boolean {
            val meta = item.itemMeta ?: return false
            val key = NamespacedKey(HeartGame.instance, "heart_emerald")
            return meta.persistentDataContainer.has(key, PersistentDataType.STRING)
        }
    }
}
