package au.cassa.paxton.listener

import au.cassa.paxton.Paxton
import net.dv8tion.jda.api.hooks.ListenerAdapter

@Suppress("unused") // <------ remove this if you decide to add some listeners!
object ListenerManager {

    val listeners = linkedSetOf<ListenerAdapter>(
        // add listener objects here
    )

    fun load() {
        listeners.forEach { Paxton.shardManager.addEventListener(it) }
    }

}