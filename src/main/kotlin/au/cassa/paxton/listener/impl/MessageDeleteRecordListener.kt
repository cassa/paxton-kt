package au.cassa.paxton.listener.impl

import au.cassa.paxton.userlog.LoggedMsg
import au.cassa.paxton.userlog.UserlogManager
import au.cassa.paxton.util.StringUtils
import net.dv8tion.jda.api.events.message.MessageDeleteEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*

object MessageDeleteRecordListener : ListenerAdapter() {

    const val MSG_TRUNCATED_LEN = 256

    override fun onMessageDelete(event: MessageDeleteEvent) {
        if (!event.isFromGuild) return

        val previouslyLoggedMsg: LoggedMsg? = UserlogManager.findLoggedMsgByDisMsgId(event.messageIdLong)

        if(previouslyLoggedMsg == null) {
            UserlogManager.insertMessageDeleteRecord(
                authorDisUserId = null,
                authorUsername = null,
                timestamp = Date(),
                guildId = event.guild.idLong,
                channelId = event.channel.idLong,
                channelName = event.channel.name,
                messageId = event.messageIdLong,
                msgTruncated = null
            )
        } else {
            UserlogManager.insertMessageDeleteRecord(
                authorDisUserId = previouslyLoggedMsg.disUserId,
                authorUsername = previouslyLoggedMsg.username,
                // ^^ NOTE: this may be an outdated username as it was the username used when the message was first
                //          created. Don't want to create extra work for the bot to decipher the new username, waste
                //          of resources.
                timestamp = Date(),
                guildId = event.guild.idLong,
                channelId = event.channel.idLong,
                channelName = event.channel.name,
                messageId = event.messageIdLong,
                msgTruncated = StringUtils.truncate(previouslyLoggedMsg.latestMsg, MSG_TRUNCATED_LEN)
            )
        }
    }

}