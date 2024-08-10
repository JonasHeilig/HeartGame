package de.jonasheilig.heartGame.recipes

import de.jonasheilig.heartGame.items.EmptyHeartContainer
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.Plugin

class EmptyHeartContainerRecipe(private val plugin: Plugin) {

    fun registerRecipe() {
        val key = NamespacedKey(plugin, "empty_heart_container_recipe")
        val recipe = ShapedRecipe(key, EmptyHeartContainer.create())

        recipe.shape(
            " O ",
            "GBG",
            " G "
        )

        recipe.setIngredient('G', Material.GLASS)
        recipe.setIngredient('B', Material.GLASS_BOTTLE)
        recipe.setIngredient('O', Material.OBSIDIAN)

        plugin.server.addRecipe(recipe)
    }
}
