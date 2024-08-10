package de.jonasheilig.heartGame.recipes

import de.jonasheilig.heartGame.items.HeartEmerald
import de.jonasheilig.heartGame.items.ReviveBeaconPart
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.Plugin

class ReviveBeaconPartRecipe(private val plugin: Plugin) {
    fun registerRecipe() {
        val key = NamespacedKey(plugin, "revive_beacon_part_recipe")
        val recipe = ShapedRecipe(key, ReviveBeaconPart.create())

        recipe.shape(
            "NNN",
            "NED",
            "DDD"
        )

        recipe.setIngredient('E', HeartEmerald.create().type)
        recipe.setIngredient('D', Material.DIAMOND)
        recipe.setIngredient('N', Material.NETHERITE_INGOT)

        plugin.server.addRecipe(recipe)
    }
}