package au.cassa.paxton.userlog

import au.cassa.paxton.data.DatabaseManager.dbConnection
import au.cassa.paxton.util.DatabaseUtils.getNullableLongFromResultSet
import net.dv8tion.jda.api.entities.Message
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import java.sql.Types
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

            val statement: Statement = dbConnection.createStatement()

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

        fun getLoggedMsgById(
            recMsgId: Long
        ): LoggedMsg {
            val statement: PreparedStatement = dbConnection.prepareStatement(
                """
                   SELECT *
                   FROM LogMessage
                   WHERE id = ?
                   LIMIT 1;
                """.trimIndent()
            )

            statement.setLong(1, recMsgId)

            val resultSet: ResultSet = statement.executeQuery()

            if (!resultSet.next()) {
                throw IllegalArgumentException("Unable to query LogMessage record with id = $recMsgId")
            }

            val loggedMsg = LoggedMsg(
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
                getNullableLongFromResultSet(resultSet, 16), // referenced msg id
                resultSet.getString(17) // attachments summary
            )

            statement.close()

            return loggedMsg
        }

        val recMsgId: Long = tryFindRecordId(disMsgId) ?: return null
        return getLoggedMsgById(recMsgId)
    }

    fun insertGuildMemberUpdateAvatarRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        guildId: Long,
        oldAvatarUrl: String?,
        newAvatarUrl: String?
    ) {
        val prepStatement = dbConnection.prepareStatement(
            """
                INSERT INTO LogGuildMemberUpdateAvatar
                    (dis_user_id, username, timestamp, guild_id, old_avatar_url, new_avatar_url)
                VALUES
                    (?,           ?,        ?,         ?,        ?,              ?)
                ;
            """.trimIndent()
        )
        prepStatement.setLong(1, disUserId)
        prepStatement.setString(2, username)
        prepStatement.setTimestamp(3, java.sql.Timestamp(timestamp.time))
        prepStatement.setLong(4, guildId)
        prepStatement.setString(5, oldAvatarUrl)
        prepStatement.setString(6, newAvatarUrl)
        prepStatement.executeUpdate()
        prepStatement.close()
    }

    fun insertUserUpdateNameRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        oldName: String?,
        newName: String?
    ) {
        val prepStatement = dbConnection.prepareStatement(
            """
                INSERT INTO LogUserUpdateName
                    (dis_user_id, username, timestamp, old_name, new_name)
                VALUES
                    (?,           ?,        ?,         ?,        ?)
                ;
            """.trimIndent()
        )
        prepStatement.setLong(1, disUserId)
        prepStatement.setString(2, username)
        prepStatement.setTimestamp(3, java.sql.Timestamp(timestamp.time))
        prepStatement.setString(4, oldName)
        prepStatement.setString(5, newName)
        prepStatement.executeUpdate()
        prepStatement.close()
    }

    fun insertGuildMemberJoinRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        guildId: Long,
        displayName: String?,
        userCount: Int
    ) {
        val prepStatement = dbConnection.prepareStatement(
            """
                INSERT INTO LogGuildMemberJoin
                    (dis_user_id, username, timestamp, guild_id, displayname, user_count)
                VALUES
                    (?,           ?,        ?,         ?,        ?,           ?)
                ;
            """.trimIndent()
        )
        prepStatement.setLong(1, disUserId)
        prepStatement.setString(2, username)
        prepStatement.setTimestamp(3, java.sql.Timestamp(timestamp.time))
        prepStatement.setLong(4, guildId)
        prepStatement.setString(5, displayName)
        prepStatement.setInt(6, userCount)
        prepStatement.executeUpdate()
        prepStatement.close()
    }

    fun insertGuildMemberRemoveRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        guildId: Long,
        displayName: String?,
        userCount: Int
    ) {
        with(dbConnection.prepareStatement(
            """
                INSERT INTO LogGuildMemberRemove
                    (dis_user_id, username, timestamp, guild_id, displayname, user_count)
                VALUES
                    (?,           ?,        ?,         ?,        ?,           ?)
                ;
            """.trimIndent()
        )) {
            setLong(1, disUserId)
            setString(2, username)
            setTimestamp(3, java.sql.Timestamp(timestamp.time))
            setLong(4, guildId)
            setString(5, displayName)
            setInt(6, userCount)
            executeUpdate()
            close()
        }
    }

    fun insertGuildMemberUpdateNicknameRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        guildId: Long,
        oldNickname: String?,
        newNickname: String?
    ) {
        with(dbConnection.prepareStatement(
            """
                INSERT INTO LogGuildMemberUpdateNickname
                    (dis_user_id, username, timestamp, guild_id, old_nickname, new_nickname)
                VALUES
                    (?,           ?,        ?,         ?,        ?,            ?)
                ;
            """.trimIndent()
        )) {
            setLong(1, disUserId)
            setString(2, username)
            setTimestamp(3, java.sql.Timestamp(timestamp.time))
            setLong(4, guildId)
            setString(5, oldNickname)
            setString(6, newNickname)
            executeUpdate()
            close()
        }
    }

    fun insertGuildVoiceGuildMuteRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        guildId: Long,
        newState: Boolean,
    ) {
        with(dbConnection.prepareStatement(
            """
                INSERT INTO LogGuildVoiceGuildMute
                    (dis_user_id, username, timestamp, guild_id, new_state)
                VALUES
                    (?,           ?,        ?,         ?,        ?)
                ;
            """.trimIndent()
        )) {
            setLong(1, disUserId)
            setString(2, username)
            setTimestamp(3, java.sql.Timestamp(timestamp.time))
            setLong(4, guildId)
            setBoolean(5, newState)
            executeUpdate()
            close()
        }
    }

    fun insertGuildVoiceGuildDeafenRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        guildId: Long,
        newState: Boolean,
    ) {
        with(dbConnection.prepareStatement(
            """
                INSERT INTO LogGuildVoiceGuildDeafen
                    (dis_user_id, username, timestamp, guild_id, new_state)
                VALUES
                    (?,           ?,        ?,         ?,        ?)
                ;
            """.trimIndent()
        )) {
            setLong(1, disUserId)
            setString(2, username)
            setTimestamp(3, java.sql.Timestamp(timestamp.time))
            setLong(4, guildId)
            setBoolean(5, newState)
            executeUpdate()
            close()
        }
    }

    @Suppress("DuplicatedCode")
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
        val prepStatement = dbConnection.prepareStatement(
            """
                INSERT INTO LogMessageDelete
                    (author_dis_user_id, author_username, timestamp, guild_id, channel_id, channel_name, message_id, msg_truncated)
                VALUES
                    (?,                  ?,               ?,         ?,        ?,          ?,            ?,          ?)
                ;
            """.trimIndent()
        )

        if(authorDisUserId == null) {
            prepStatement.setNull(1, Types.BIGINT)
        } else {
            prepStatement.setLong(1, authorDisUserId)
        }

        with(prepStatement) {
            setString(2, authorUsername)
            setTimestamp(3, java.sql.Timestamp(timestamp.time))
            setLong(4, guildId)
            setLong(5, channelId)
            setString(6, channelName)
            setLong(7, messageId)
            setString(8, msgTruncated)
            executeUpdate()
            close()
        }
    }

    fun insertMessageReceived(
        msg: LoggedMsg
    ) {
        // insert record into LogMessage
        val statement = dbConnection.prepareStatement(
            """
                INSERT INTO LogMessage
                    (dis_user_id, username, received_timestamp, last_update_timestamp, guild_id, channel_id,
                    channel_name, channel_type, message_id, original_msg, latest_msg, msg_type, msg_jump_url,
                    referenced_msg_id, attachments_summary)
                VALUES
                    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ;
            """.trimIndent()
        )

        with(statement) {
            setLong(1, msg.disUserId)
            setString(2, msg.username)
            setTimestamp(3, java.sql.Timestamp(msg.receivedTimestamp.time))
            setTimestamp(4, msg.lastUpdateTimestamp?.let { java.sql.Timestamp(it.time) }) // `let` for nullable date
            setLong(5, msg.guildId)
            setLong(6, msg.channelId)
            setString(7, msg.channelName)
            setString(8, msg.channelType)
            setLong(9, msg.messageId)
            setString(10, msg.originalMsg)
            setString(11, msg.latestMsg)
            setString(12, msg.msgType)
            setString(13, msg.msgJumpUrl)
            // note: parameters 14 and 15 are handled below.
            // note: executing update and closing statement is handled below.
        }

        if(msg.referencedMsgId == null) {
            statement.setNull(14, Types.BIGINT)
        } else {
            statement.setLong(14, msg.referencedMsgId)
        }

        statement.setString(15, msg.attachmentsSummary)
        statement.executeUpdate()
        statement.close()
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
        // Insert LogMessageUpdate record
        with(dbConnection.prepareStatement(
            """
                INSERT INTO LogMessageUpdate
                    (dis_user_id, username, timestamp, guild_id, channel_id,
                    channel_name, message_id, old_msg, new_msg, attachments_summary)
                VALUES
                    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ;
            """.trimIndent()
        )) {
            setLong(1, disUserId)
            setString(2, username)
            setTimestamp(3, java.sql.Timestamp(timestamp.time))
            setLong(4, guildId)
            setLong(5, channelId)
            setString(6, channelName)
            setLong(7, messageId)
            setString(8, oldMsg)
            setString(9, newMsg)
            setString(10, attachmentsSummary)
            executeUpdate()
            close()
        }

        // Update LogMessage record
        with(dbConnection.prepareStatement(
            """
                UPDATE LogMessage
                SET
                    last_update_timestamp = ?,
                    latest_msg = ?
                WHERE message_id = ?;
            """.trimIndent()
        )) {
            setTimestamp(1, java.sql.Timestamp(timestamp.time))
            setString(2, newMsg)
            setLong(3, messageId)
            executeUpdate()
            close()
        }
    }

    @Suppress("DuplicatedCode")
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
        with(dbConnection.prepareStatement(
            """
                INSERT INTO LogUserActivityStart
                    (dis_user_id, username, timestamp, type, name, url, is_rich_presence, emoji_name)
                VALUES
                    (?,           ?,        ?,         ?,    ?,    ?,   ?,                ?)
                ;
            """.trimIndent()
        )) {
            setLong(1, disUserId)
            setString(2, username)
            setTimestamp(3, java.sql.Timestamp(timestamp.time))
            setString(4, type)
            setString(5, name)
            setString(6, url)
            setBoolean(7, isRichPresence)
            setString(8, emojiName)
            executeUpdate()
            close()
        }
    }

    @Suppress("DuplicatedCode")
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
        with(dbConnection.prepareStatement(
            """
                INSERT INTO LogUserActivityEnd
                    (dis_user_id, username, timestamp, type, name, url, is_rich_presence, emoji_name)
                VALUES
                    (?,           ?,        ?,         ?,    ?,    ?,   ?,                ?)
                ;
            """.trimIndent()
        )) {
            setLong(1, disUserId)
            setString(2, username)
            setTimestamp(3, java.sql.Timestamp(timestamp.time))
            setString(4, type)
            setString(5, name)
            setString(6, url)
            setBoolean(7, isRichPresence)
            setString(8, emojiName)
            executeUpdate()
            close()
        }
    }

    fun insertUserUpdateGlobalNameRecord(
        disUserId: Long,
        username: String,
        timestamp: Date,
        oldName: String?,
        newName: String?
    ) {
        with(dbConnection.prepareStatement(
            """
                INSERT INTO LogUserUpdateGlobalName
                    (dis_user_id, username, timestamp, old_name, new_name)
                VALUES
                    (?,           ?,        ?,         ?,        ?)
                ;
            """.trimIndent()
        )) {
            setLong(1, disUserId)
            setString(2, username)
            setTimestamp(3, java.sql.Timestamp(timestamp.time))
            setString(4, oldName)
            setString(5, newName)
            executeUpdate()
            close()
        }
    }

}