package au.cassa.paxton.userlog

import net.dv8tion.jda.api.entities.Message
import java.util.*

data class LoggedMsg (
    val disUserId: Long,
    val username: String,
    val receivedTimestamp: Date,
    val lastUpdateTimestamp: Date?,
    val guildId: Long,
    val channelId: Long,
    val channelName: String,
    val channelType: String,
    val messageId: Long,
    val originalMsg: String,
    val latestMsg: String,
    val msgType: String,
    val msgJumpUrl: String,
    val referencedMsgId: Long?,
    val attachmentsSummary: String?
) {

    constructor(message: Message) : this(
        disUserId = message.author.idLong,
        username = message.author.name,
        receivedTimestamp = Date(),
        lastUpdateTimestamp = null,
        guildId = message.guild.idLong,
        channelId = message.channel.idLong,
        channelName = message.channel.name,
        channelType = message.channel.type.name,
        messageId = message.idLong,
        originalMsg = message.contentRaw,
        latestMsg = message.contentRaw,
        msgType = message.type.name,
        msgJumpUrl = message.jumpUrl,
        referencedMsgId = message.referencedMessage?.idLong,
        attachmentsSummary = UserlogManager.createAttachmentSummary(message)
    )

}
