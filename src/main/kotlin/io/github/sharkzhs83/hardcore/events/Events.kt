package io.github.sharkzhs83.hardcore.events

import io.github.sharkzhs83.hardcore.HARDCORE
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class Events : Listener {

    //크리퍼 억까
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block

        val range = 1..100
        val num = range.random()

        val superRange = 1..2
        val superNum = superRange.random()

        if(num == 1 || num == 2 || num == 3) {
            if(superNum == 1) {
                val creeper1 = player.world.spawn(block.location, Creeper::class.java)
                val creeper2 = player.world.spawn(block.location, Creeper::class.java)
                creeper1.isPowered = true
                creeper2.isPowered = true
                player.sendMessage("${ChatColor.GREEN}크리퍼들이 나타났습니다!")
            }
            else {
                val creeper = player.world.spawn(block.location, Creeper::class.java)
                creeper.isPowered = true
                player.sendMessage("${ChatColor.GREEN}크리퍼가 나타났습니다!")
            }
        }


    }

    //광물 억까
    @EventHandler
    fun onBlockBreakOre(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block

        val range = 1..5
        val num = range.random()


        if(num == 1) {
            if(block.type == Material.IRON_ORE) {
                val newItem = ItemStack(Material.COAL, 2)

                block.world.dropItemNaturally(block.location, newItem)
            }
            else if(block.type == Material.GOLD_ORE) {
                val newItem = ItemStack(Material.RAW_IRON, 2)

                block.world.dropItemNaturally(block.location, newItem)
            }
            else if(block.type == Material.LAPIS_ORE) {
                val newItem = ItemStack(Material.ICE, 2)

                block.world.dropItemNaturally(block.location, newItem)
            }
            else if(block.type == Material.DIAMOND_ORE) {
                val newItem = ItemStack(Material.LAPIS_LAZULI, 2)

                block.world.dropItemNaturally(block.location, newItem)
            }
        }


    }


    //부활
    @EventHandler
    fun onExplode(event: EntityExplodeEvent) {
        val entity = event.entity
        val entitytype = event.entityType

        if(entitytype == EntityType.CREEPER) {
            val tnt = entity.world.spawn(entity.location, TNTPrimed::class.java)
        }
        else if (entitytype == EntityType.PRIMED_TNT) {
            val creeper = entity.world.spawn(entity.location, Creeper::class.java)
            creeper.isPowered = true
        }
    }

    //스켈레톤 억까
    @EventHandler
    fun onSkeletonShoot(event: EntityShootBowEvent) {

        if(event.entityType == EntityType.SKELETON) {
            val entity = event.entity
            val arrow = entity.launchProjectile(Fireball::class.java, entity.location.direction)
        }
    }

    //좀비 억까
    @EventHandler
    fun onPlayerDamageByZombie(event: EntityDamageByEntityEvent) {
        if (event.damager.type == EntityType.ZOMBIE && event.entity is Player) {
            val player = event.entity as Player
            val mainHandItem = player.inventory.itemInMainHand
            val offHandItem = player.inventory.itemInOffHand

            var stolenMainItem: ItemStack? = null
            var stolenOffItem: ItemStack? = null


            if (mainHandItem.type != Material.AIR) {
                stolenMainItem = mainHandItem.clone()
                player.inventory.setItemInMainHand(ItemStack(Material.AIR))
            }


            if (stolenMainItem != null) {
                val zombie = event.damager as Zombie
                zombie.world.dropItem(zombie.location, stolenMainItem)
            }

            if (offHandItem.type != Material.AIR) {
                stolenOffItem = offHandItem.clone()
                player.inventory.setItemInOffHand(ItemStack(Material.AIR))
            }


            if (stolenOffItem != null) {
                val zombie = event.damager as Zombie
                zombie.world.dropItem(zombie.location, stolenOffItem)
            }
        }
    }

    //거미 억까
    @EventHandler
    fun onPlayerDamageBySpider(event: EntityDamageByEntityEvent) {
        if (event.damager.type == EntityType.SPIDER && event.entity is Player) {
            val player = event.entity as Player
            val entity = event.damager as LivingEntity

            player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 100, 5,false, false))
            entity.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 100,1))



        }
    }

    //아이템 억까
    @EventHandler
    fun onMake(event: CraftItemEvent) {
        val player = event.whoClicked
        val range = 1..5
        val num = range.random()

        if(num == 1) {
            event.currentItem = ItemStack(Material.AIR)
            if (player is Player) {
                for (ingredient in event.inventory.matrix) {
                    if (ingredient != null && ingredient.type != Material.AIR) {
                        ingredient.amount = ingredient.amount - 1
                        player.sendMessage("${ChatColor.RED}제작에 실패하였습니다!")
                        player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 0.5f, 1.0f)
                    }
                }
            }
        }


    }

}

