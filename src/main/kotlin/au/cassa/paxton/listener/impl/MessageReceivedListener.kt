package au.cassa.paxton.listener.impl

import au.cassa.paxton.userlog.LoggedMsg
import au.cassa.paxton.userlog.UserlogManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object MessageReceivedListener : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.isFromGuild) return

        UserlogManager.insertMessageReceived(
            LoggedMsg(event.message)
        )
    }

}