package au.cassa.paxton.listener

import au.cassa.paxton.Paxton
import net.dv8tion.jda.api.hooks.ListenerAdapter

object ListenerManager {

    val listeners: LinkedHashSet<ListenerAdapter> = linkedSetOf(
        // add listener objects here
        // TODO
    )

    fun load() {
        listeners.forEach { Paxton.shardManager.addEventListener(it) }
    }

}