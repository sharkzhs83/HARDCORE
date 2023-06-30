package io.github.sharkzhs83.hardcore


import io.github.sharkzhs83.hardcore.events.Events
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
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



        val dragonItem = ItemStack(Material.NETHER_STAR)
        val dragonMeta = dragonItem.itemMeta
        dragonMeta.setDisplayName("${org.bukkit.ChatColor.LIGHT_PURPLE}드래곤의 별")
        dragonItem.itemMeta = dragonMeta

        val witherItem = ItemStack(Material.NETHER_STAR)
        val witherMeta = witherItem.itemMeta
        witherMeta.setDisplayName("${org.bukkit.ChatColor.BLACK}위더의 별")
        witherItem.itemMeta = witherMeta

        val elderItem = ItemStack(Material.NETHER_STAR)
        val elderMeta = elderItem.itemMeta
        elderMeta.setDisplayName("${org.bukkit.ChatColor.BLUE}엘더가디언의 별")
        elderItem.itemMeta = elderMeta

        val wardenItem = ItemStack(Material.NETHER_STAR)
        val wardenMeta = wardenItem.itemMeta
        wardenMeta.setDisplayName("${org.bukkit.ChatColor.DARK_BLUE}워든의 별")
        wardenItem.itemMeta = wardenMeta



        val B = ItemStack(Material.DISC_FRAGMENT_5)
        val BM = defender.itemMeta

        BM.setDisplayName("${ChatColor.AQUA}무언가의 조각")
        B.itemMeta = BM


        val BRecipeKey = NamespacedKey(this, "Boss")
        val BRecipe = ShapedRecipe(BRecipeKey, B)
        BRecipe.shape(" D ","NSW"," E ")
        BRecipe.setIngredient('D', dragonItem)
        BRecipe.setIngredient('N', witherItem)
        BRecipe.setIngredient('S', Material.HEART_OF_THE_SEA)
        BRecipe.setIngredient('W', wardenItem)
        BRecipe.setIngredient('E', elderItem)
        server.addRecipe(BRecipe)



    }


    override fun onDisable() {
        logger.info("HARDCORE has disabled")
    }
}


