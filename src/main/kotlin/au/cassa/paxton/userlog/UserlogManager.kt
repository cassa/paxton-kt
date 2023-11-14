package au.cassa.paxton.userlog

import au.cassa.paxton.data.DatabaseManager
import net.dv8tion.jda.api.entities.Message
import java.sql.ResultSet
import java.sql.Statement
import java.util.*

object UserlogManager {

    fun createAttachmentSummary(
        msg: Message
    ): String? {
        if (msg.attachments.isEmpty()) return null

        val builder = StringBuilder()

        for (attachment in msg.attachments) {
            builder.append("{name: \"${attachment.fileName}\",")
            builder.append("size: \"${attachment.size}\",")
            builder.append("url: \"${attachment.url}\",")
            builder.append("type: \"${attachment.contentType}\",")
            builder.append("desc: \"${attachment.description}\"},")
        }

        return "[${builder}]"
    }

    fun findLoggedMsgByDisMsgId(
        disMsgId: Long
    ): LoggedMsg? {

        fun tryFindRecordId(
            disMsgId: Long
        ): Long? {
            var ret: Long? = null

            val statement: Statement = DatabaseManager.connection.createStatement()

            val resultSet: ResultSet = statement.executeQuery(
                """
                    SELECT id
                    FROM LogMessage
                    WHERE message_id = $disMsgId
                    ORDER BY received_timestamp DESC
                    LIMIT 1;
                """.trimIndent()
            )

            if (resultSet.next()) {
                ret = resultSet.getLong(1)
            }

            statement.close()

            return ret
        }

        fun rsGetNullableLong(resultSet: ResultSet, columnIndex: Int): Long? {
            val l = resultSet.getLong(columnIndex)
            return if (resultSet.wasNull()) null else l
        }

        fun serializeRecord(
            recMsgId: Long
        ): LoggedMsg {
            val statement: Statement = DatabaseManager.connection.createStatement()

            val resultSet: ResultSet = statement.executeQuery(
                """
                   SELECT *
                   FROM LogMessage
                   WHERE id = $recMsgId
                   LIMIT 1;
                """.trimIndent()
            )

            if (!resultSet.next()) {
                throw IllegalArgumentException("Unable to query LogMessage record with id = $recMsgId")
            }

            return LoggedMsg(
                resultSet.getLong(3), // dis user id
                resultSet.getString(4), // username
                resultSet.getDate(5), // received timestamp
                resultSet.getDate(6), // last update timestamp
                resultSet.getLong(7), // guild id
                resultSet.getLong(8), // channel id
                resultSet.getString(9), // channel name
                resultSet.getString(10), // channel type
                resultSet.getLong(11), // dis message id
                resultSet.getString(12), // original msg
                resultSet.getString(13), // latest msg
                resultSet.getString(14), // msg type
                resultSet.getString(15), // msg jump url
                rsGetNullableLong(resultSet, 16), // referenced msg id
                resultSet.getString(17) // attachments summary
            )
        }

        val recMsgId: Long = tryFindRecordId(disMsgId) ?: return null
        return serializeRecord(recMsgId)
    }

    fun insertGuildMemberUpdateAvatarRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        guildId: Long,
        oldAvatarUrl: String?,
        newAvatarUrl: String?
    ) {
        // todo insert record into LogGuildMemberUpdateAvatar
        TODO("Not yet implemented")
    }

    fun insertUserUpdateNameRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        oldName: String?,
        newName: String?
    ) {
        // todo insert record into LogUserUpdateName
        TODO("Not yet implemented")
    }

    fun insertGuildMemberJoinRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        guildId: Long,
        displayName: String?,
        userCount: Int
    ) {
        // todo insert record into LogGuildMemberJoin
        TODO("Not yet implemented")
    }

    fun insertGuildMemberRemoveRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        guildId: Long,
        displayName: String?,
        userCount: Int
    ) {
        // todo insert record into LogGuildMemberRemove
        TODO("Not yet implemented")
    }

    fun insertGuildMemberUpdateNicknameRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        guildId: Long,
        oldNickname: String?,
        newNickname: String?
    ) {
        // todo insert record into LogGuildMemberUpdateNickname
        TODO("Not yet implemented")
    }

    fun insertGuildVoiceGuildMuteRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        guildId: Long,
        newState: Boolean,
    ) {
        // todo insert record into LogGuildVoiceGuildMute
        TODO("Not yet implemented")
    }

    fun insertGuildVoiceGuildDeafenRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        guildId: Long,
        newState: Boolean,
    ) {
        // todo insert record into LogGuildVoiceGuildDeafen
        TODO("Not yet implemented")
    }

    fun insertMessageDeleteRecord(
        authorDisUserId: Long?,
        authorUsername: String?,
        timestamp: Date,
        guildId: Long,
        channelId: Long,
        channelName: String,
        messageId: Long,
        msgTruncated: String?
    ) {
        // todo insert record into LogMessageDelete
        TODO("Not yet implemented")
    }

    fun insertMessageReceived(
        loggedMsg: LoggedMsg
    ) {
        // todo insert record into LogMessage
        TODO("Not yet implemented")
    }

    fun insertMessageUpdate(
        disUserId: Long,
        username: String,
        timestamp: Date,
        guildId: Long,
        channelId: Long,
        channelName: String,
        messageId: Long,
        oldMsg: String?,
        newMsg: String,
        attachmentsSummary: String?
    ) {
        // TODO Insert LogMessageUpdate record
        // TODO Update LogMessage record
        TODO("Not yet implemented")
    }

    fun insertUserActivityStartRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        type: String,
        name: String,
        url: String?,
        isRichPresence: Boolean,
        emojiName: String?
    ) {
        // TODO insert record into LogUserActivityStart
        TODO("Not yet implemented")
    }

    fun insertUserActivityEndRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        type: String,
        name: String,
        url: String?,
        isRichPresence: Boolean,
        emojiName: String?
    ) {
        // TODO insert record into LogUserActivityEnd
        TODO("Not yet implemented")
    }

    fun insertUserUpdateGlobalNameRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        oldName: String?,
        newName: String?
    ) {
        // TODO insert record into LogUserUpdateGlobalName
        TODO("Not yet implemented")
    }

}