package au.cassa.paxton.listener

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object TestListener : ListenerAdapter() {

    override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
        if(event.user == null) return

        event.channel.sendMessage("[Test]: ${event.user!!.asMention} reacted").queue()
    }

}