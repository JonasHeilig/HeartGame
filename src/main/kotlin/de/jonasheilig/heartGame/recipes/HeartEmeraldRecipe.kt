package de.jonasheilig.heartGame.recipes

import de.jonasheilig.heartGame.items.HeartEmerald
import de.jonasheilig.heartGame.items.ReviveBeaconPart
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.Plugin

class HeartEmeraldRecipe(private val plugin: Plugin) {

    fun registerRecipe() {
        val key = NamespacedKey(plugin, "heart_emerald_recipe")
        val recipe = ShapedRecipe(key, HeartEmerald.create())

        recipe.shape(
            "GLG",
            "ONO",
            "EOE"
        )

        recipe.setIngredient('E', Material.EMERALD)
        recipe.setIngredient('O', Material.OBSIDIAN)
        recipe.setIngredient('L', Material.GLASS)
        recipe.setIngredient('G', Material.GOLD_INGOT)
        recipe.setIngredient('N', ReviveBeaconPart.create().type)

        plugin.server.addRecipe(recipe)
    }
}
