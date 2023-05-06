package io.github.sharkzhs83.hardcore.events

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.player.PlayerBedEnterEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class Events : Listener {

    //크리퍼 억까
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block

        val range = 1..500
        val num = range.random()

        val superRange = 1..2
        val superNum = superRange.random()

        val defender = ItemStack(Material.EMERALD)
        val defMeta = defender.itemMeta

        defMeta.setDisplayName("${net.md_5.bungee.api.ChatColor.AQUA}크리퍼 저항 에메랄드")
        defender.itemMeta = defMeta



        if (num == 1 || num == 2 || num == 3) {
            if (superNum == 1) {
                if (player.inventory.itemInOffHand == defender) {
                    player.sendMessage("${ChatColor.AQUA}크리퍼 출현을 방어했습니다!")
                    if(player.inventory.itemInOffHand.amount == 1) {
                        player.inventory.setItemInOffHand(ItemStack(Material.AIR))
                    }
                    else {
                        player.inventory.itemInOffHand.amount -= 1
                    }
                } else {
                    val creeper1 = player.world.spawn(block.location, Creeper::class.java)
                    val creeper2 = player.world.spawn(block.location, Creeper::class.java)
                    creeper1.isPowered = true
                    creeper2.isPowered = true
                    player.sendMessage("${ChatColor.GREEN}크리퍼들이 나타났습니다!")
                }

            }
            if (player.inventory.itemInOffHand == defender) {
                player.sendMessage("${ChatColor.AQUA}크리퍼 출현을 방어했습니다!")
                if(player.inventory.itemInOffHand.amount == 1) {
                    player.inventory.setItemInOffHand(ItemStack(Material.AIR))
                }
                else {
                    player.inventory.itemInOffHand.amount -= 1
                }
            } else {
                val creeper = player.world.spawn(block.location, Creeper::class.java)
                creeper.isPowered = true
                player.sendMessage("${ChatColor.GREEN}크리퍼가 나타났습니다!")
            }
        }
    }

    //defender 드랍 & 보스
    @EventHandler
    fun onEntityDead(event: EntityDeathEvent) {
        if(event.entity.type == EntityType.ENDER_DRAGON) {
            Bukkit.broadcast(Component.text("엔더드래곤을 격파하였습니다!", TextColor.color(181, 76, 250)))
        }
        else if(event.entity.type == EntityType.WITHER) {
            Bukkit.broadcast(Component.text("위더를 격파하였습니다!", TextColor.color(0, 0, 0)))
        }
        else if(event.entity.type == EntityType.ELDER_GUARDIAN) {
            Bukkit.broadcast(Component.text("엘더가디언을 격파하였습니다!", TextColor.color(48, 231, 238)))
            val item = ItemStack(Material.NETHER_STAR)
            val meta = item.itemMeta
            meta.setDisplayName("${ChatColor.BLUE}엘더가디언의 별")
            item.itemMeta = meta

            event.drops.add(item)
        }
        else if(event.entity.type == EntityType.WARDEN) {
            Bukkit.broadcast(Component.text("워든을 격파하였습니다!", TextColor.color(12, 12, 131)))

            val item = ItemStack(Material.NETHER_STAR)
            val meta = item.itemMeta
            meta.setDisplayName("${ChatColor.DARK_BLUE}워든의 별")
            item.itemMeta = meta

            event.drops.add(item)

        }
        else {
            val defender = ItemStack(Material.EMERALD)
            val defMeta = defender.itemMeta

            defMeta.setDisplayName("${net.md_5.bungee.api.ChatColor.AQUA}크리퍼 저항 에메랄드")
            defender.itemMeta = defMeta

            val range = 1..50
            val num = range.random()
            if(num == 1) {
                event.drops.add(defender)
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

        if (num == 1) {
            if (block.type == Material.IRON_ORE || block.type == Material.DEEPSLATE_IRON_ORE) {
                event.isDropItems = false
                val newItem = ItemStack(Material.COAL, 2)
                player.sendMessage("${ChatColor.BLACK}철인줄 알았는데 석탄이었다..")
                block.world.dropItemNaturally(block.location, newItem)
            } else if (block.type == Material.GOLD_ORE || block.type == Material.DEEPSLATE_GOLD_ORE) {
                event.isDropItems = false
                val newItem = ItemStack(Material.RAW_IRON, 2)
                player.sendMessage("${ChatColor.GREEN}금인줄 알았는데 철이었다..")
                block.world.dropItemNaturally(block.location, newItem)
            } else if (block.type == Material.LAPIS_ORE || block.type == Material.DEEPSLATE_LAPIS_ORE) {
                event.isDropItems = false
                val newItem = ItemStack(Material.ICE, 2)
                player.sendMessage("${ChatColor.AQUA}청금석인줄 알았는데 얼음이었다..")
                block.world.dropItemNaturally(block.location, newItem)
            } else if (block.type == Material.DIAMOND_ORE || block.type == Material.DEEPSLATE_DIAMOND_ORE) {
                event.isDropItems = false
                val newItem = ItemStack(Material.LAPIS_LAZULI, 2)
                player.sendMessage("${ChatColor.BLUE}다이아몬드인줄 알았는데 청금석이었다..")
                block.world.dropItemNaturally(block.location, newItem)
            }
        }
    }


    //크리퍼 부활
    @EventHandler
    fun onExplode(event: EntityExplodeEvent) {
        val entity = event.entity
        val entitytype = event.entityType

        if (entitytype == EntityType.CREEPER) {
            val tnt = entity.world.spawn(entity.location, TNTPrimed::class.java)
        } else if (entitytype == EntityType.PRIMED_TNT) {
            val creeper = entity.world.spawn(entity.location, Creeper::class.java)
            creeper.isPowered = true
        }
    }

    //스켈레톤 억까
    @EventHandler
    fun onSkeletonShoot(event: EntityShootBowEvent) {
        if (event.entityType == EntityType.SKELETON) {
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

            player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 100, 5, false, false))
            entity.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 100, 1))


        }
    }

    //엔더맨 억까
    @EventHandler
    fun onPlayerDamageByEnderMan(event: EntityDamageByEntityEvent) {
        if (event.damager.type == EntityType.ENDERMAN && event.entity is Player) {
            val player = event.entity as Player
            val entity = event.damager as LivingEntity

            player.teleport(player.location.add(0.0, 30.0, 0.0))
            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 0.7f, 1.0f)

        }
    }


    //네더 좀비 돼지
    @EventHandler
    fun onPlayerDamageByZombifiedPiglin(event: EntityDamageByEntityEvent) {
        if(event.damager.type == EntityType.ZOMBIFIED_PIGLIN && event.entity is Player) {
            val player = event.entity as Player
            val entity = event.damager as LivingEntity

            player.world.spawn(entity.location.add(0.0,2.00,0.0), PigZombie::class.java)
            player.playSound(player.location, Sound.ITEM_GOAT_HORN_SOUND_0, 0.7f, 1.5f)
        }
    }

    //네더 피글린들
    @EventHandler
    fun onPlayerDamageByPiglin(event: EntityDamageByEntityEvent) {
        if(event.damager.type == EntityType.PIGLIN && event.entity is Player) {
            val player = event.entity as Player
            val piglin = event.damager as LivingEntity
            val server = player.server

            piglin.equipment?.setItemInMainHand(ItemStack(Material.NETHERITE_SWORD))
            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 0.7f, 1.2f)
        }
    }

    @EventHandler
    fun onPlayerDamageByPiglinBrute(event: EntityDamageByEntityEvent) {
        if(event.damager.type == EntityType.PIGLIN_BRUTE && event.entity is Player) {
            val player = event.entity as Player
            val piglin = event.damager as LivingEntity

            piglin.equipment?.setItemInMainHand(ItemStack(Material.NETHERITE_AXE))
            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 0.7f, 1.2f)
        }
    }

    //네더 스켈레톤 억까
    @EventHandler
    fun onPlayerDamageByWitherSkeleton(event: EntityDamageByEntityEvent) {
        if(event.damager.type == EntityType.WITHER_SKELETON && event.entity is Player) {
            val player = event.entity as Player

            player.world.spawn(player.location.add(0.0,13.00,0.0), TNTPrimed::class.java)
            player.playSound(player.location, Sound.ENTITY_TNT_PRIMED, 0.7f, 1.5f)
        }
    }

    //블레이즈 억까
    @EventHandler
    fun onPlayerDamageByBlaze(event: EntityDamageByEntityEvent) {
        if(event.damager.type == EntityType.BLAZE && event.entity is Player) {
            val player = event.entity as Player

            player.playSound(player.location, Sound.BLOCK_LAVA_AMBIENT, 0.7f, 1.5f)
            player.fireTicks = 1200
        }
    }


    //음식억까
    @EventHandler
    fun onPlayerEatFood(event: PlayerItemConsumeEvent) {
        val player = event.player
        val item = event.item

        val range = 1..5
        val num = range.random()

        if (item.type.isEdible) {
            if (num == 1) {
                player.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 60, 5, false, false))
                player.playSound(player.location, Sound.BLOCK_BAMBOO_FALL, 0.7f, 1.3f)
                player.sendMessage("${ChatColor.RED}배탈이 나서 눈 앞이 깜깜해졌다!")
            }
        }
    }


    //아이템 억까
    @EventHandler
    fun onMake(event: CraftItemEvent) {
        val player = event.whoClicked
        val range = 1..20
        val num = range.random()

        if (num == 1) {
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

    //침대 억까
    @EventHandler
    fun onSleep(event: PlayerBedEnterEvent) {
        val player = event.player
        val bed = event.bed
        val range = 1..50
        val num = range.random()

        if (num == 2) {
            val tnt = bed.world.spawn(bed.location.add(0.0, 10.0, 0.0), TNTPrimed::class.java)
            player.playSound(player.location, Sound.ENTITY_TNT_PRIMED, 0.7f, 1.5f)
            player.sendMessage("${ChatColor.RED}왠지 불안한 기분이 든다..")
            return
        }
    }
}
