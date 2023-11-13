package au.cassa.paxton.listener

import au.cassa.paxton.Paxton
import au.cassa.paxton.listener.impl.ReadyListener
import net.dv8tion.jda.api.hooks.ListenerAdapter

object ListenerManager {

    val listeners: LinkedHashSet<ListenerAdapter> = linkedSetOf(
        ReadyListener
    )

    fun load() {
        Paxton.log.info("Loading listeners")
        listeners.forEach { Paxton.shardManager.addEventListener(it) }
    }

}