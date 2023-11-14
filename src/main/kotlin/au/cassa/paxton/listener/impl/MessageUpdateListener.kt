package au.cassa.paxton.listener.impl

import au.cassa.paxton.userlog.LoggedMsg
import au.cassa.paxton.userlog.UserlogManager
import net.dv8tion.jda.api.events.message.MessageUpdateEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*

object MessageUpdateListener : ListenerAdapter() {

    override fun onMessageUpdate(event: MessageUpdateEvent) {
        if (event.isFromGuild) return

        val previousLoggedMsg: LoggedMsg? = UserlogManager.findLoggedMsgByDisMsgId(event.messageIdLong)

        UserlogManager.insertMessageUpdate(
            disUserId = event.author.idLong,
            username = event.author.name,
            timestamp = Date(),
            guildId = event.member!!.guild.idLong,
            channelId = event.channel.idLong,
            channelName = event.channel.name,
            messageId = event.messageIdLong,
            oldMsg = previousLoggedMsg?.latestMsg,
            newMsg = event.message.contentRaw,
            attachmentsSummary = UserlogManager.createAttachmentSummary(event.message)
        )
    }

}