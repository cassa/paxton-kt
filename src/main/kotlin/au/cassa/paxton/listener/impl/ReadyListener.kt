package au.cassa.paxton.listener.impl

import au.cassa.paxton.Paxton
import au.cassa.paxton.listener.ListenerManager
import au.cassa.paxton.slashcmd.SlashCmdManager
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object ReadyListener : ListenerAdapter() {

    override fun onReady(
        event: ReadyEvent
    ) {
        Paxton.log.info("Bot is ready.")
        ListenerManager.load()
        SlashCmdManager.load()
    }

}