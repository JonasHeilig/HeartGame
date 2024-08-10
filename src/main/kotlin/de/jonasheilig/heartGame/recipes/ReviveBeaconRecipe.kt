package de.jonasheilig.heartGame.recipes

import de.jonasheilig.heartGame.items.HeartEmerald
import de.jonasheilig.heartGame.items.ReviveBeaconPart
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.Plugin

class ReviveBeaconRecipe(private val plugin: Plugin) {

    fun registerRecipe() {
        val key = NamespacedKey(plugin, "revive_beacon_recipe")
        val recipe = ShapedRecipe(key, ReviveBeaconPart.create())

        recipe.shape(
            "EOE",
            "OEO",
            "EOE"
        )

        recipe.setIngredient('E', HeartEmerald.create().type)
        recipe.setIngredient('B', Material.BEACON)
        recipe.setIngredient('O', Material.OBSIDIAN)

        plugin.server.addRecipe(recipe)
    }
}
