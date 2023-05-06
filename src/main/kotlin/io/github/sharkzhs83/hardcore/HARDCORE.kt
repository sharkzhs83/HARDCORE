package io.github.sharkzhs83.hardcore


import io.github.sharkzhs83.hardcore.events.Events
import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin


class HARDCORE : JavaPlugin() ,Listener{

    override fun onEnable() {
        logger.info("HARDCORE has enabled")
        server.pluginManager.registerEvents(Events(), this)

        val defender = ItemStack(Material.EMERALD)
        val defMeta = defender.itemMeta

        defMeta.setDisplayName("${ChatColor.AQUA}크리퍼 저항 에메랄드")
        defender.itemMeta = defMeta

        val defRecipeKey = NamespacedKey(this, "defender")
        val defRecipe = ShapedRecipe(defRecipeKey, defender)
        defRecipe.shape("   ","GEG","   ")
        defRecipe.setIngredient('G', Material.GOLD_INGOT)
        defRecipe.setIngredient('E', Material.EMERALD)
        server.addRecipe(defRecipe)

        this.saveDefaultConfig()
    }


    override fun onDisable() {
        logger.info("HARDCORE has disabled")
    }
}


