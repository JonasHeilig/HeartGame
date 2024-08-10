package de.jonasheilig.heartGame.items

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import de.jonasheilig.heartGame.HeartGame
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class EmptyHeartContainer {

    companion object {
        fun create(): ItemStack {
            val item = ItemStack(Material.GLASS_BOTTLE, 1)
            val meta = item.itemMeta

            meta?.displayName(Component.text("Empty Heart Container").color(NamedTextColor.RED))
            meta?.lore(listOf(Component.text("Right-click to lose a heart and gain a Heart Emerald.").color(NamedTextColor.GRAY)))

            val key = NamespacedKey(HeartGame.instance, "heart_container")
            meta?.persistentDataContainer?.set(key, PersistentDataType.STRING, "empty_heart_container")
            item.itemMeta = meta
            return item
        }

        fun isHeartContainer(item: ItemStack): Boolean {
            val meta = item.itemMeta ?: return false
            val key = NamespacedKey(HeartGame.instance, "heart_container")
            return meta.persistentDataContainer.has(key, PersistentDataType.STRING)
        }
    }
}
