package io.github.sharkzhs83.hardcore



import io.github.sharkzhs83.hardcore.events.Events
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class HARDCORE : JavaPlugin() ,Listener{

    override fun onEnable() {
        logger.info("HARDCORE has enabled")
        server.pluginManager.registerEvents(Events(), this)
    }


    override fun onDisable() {
        logger.info("HARDCORE has disabled")
    }
}


