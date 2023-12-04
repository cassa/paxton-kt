package au.cassa.paxton.data

import au.cassa.paxton.config.impl.SecretCfg
import au.cassa.paxton.config.impl.SettingsCfg
import au.cassa.paxton.util.DatabaseUtils
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

object DatabaseManager {

    lateinit var dbConnection: Connection
        private set

    fun startup() {
        connect()
        createTables()
    }

    fun shutdown() {
        /*
        In case dbConnection has not yet initialised and the bot is shutting down,
        this will make it easier to understand why an NPE was thrown.
        This probably won't ever occur, but, y'know.
         */
        Objects.requireNonNull(dbConnection, "dbConnection")

        dbConnection.close()
    }

    private fun connect() {
        // Class.forName(MYSQL_DRIVER_CLASSPATH)

        val address: String = SettingsCfg.databaseAddress()
        val port: String = SettingsCfg.databasePort()
        val dbName: String = SettingsCfg.databaseName()
        val username: String = SecretCfg.databaseUsername()
        val password: String = SecretCfg.databasePassword()

        dbConnection = DriverManager.getConnection("jdbc:mysql://$address:$port/$dbName", username, password)
    }

    private fun createTables() {
        try {
            linkedSetOf(
                DatabaseUtils.CREATE_TABLE_LOG_GUILD_MEMBER_UPDATE_AVATAR,
                DatabaseUtils.CREATE_TABLE_LOG_USER_UPDATE_NAME,
                DatabaseUtils.CREATE_TABLE_LOG_GUILD_MEMBER_JOIN,
                DatabaseUtils.CREATE_TABLE_LOG_GUILD_MEMBER_REMOVE,
                DatabaseUtils.CREATE_TABLE_LOG_GUILD_MEMBER_UPDATE_NICKNAME,
                DatabaseUtils.CREATE_TABLE_LOG_GUILD_VOICE_GUILD_MUTE,
                DatabaseUtils.CREATE_TABLE_LOG_GUILD_VOICE_GUILD_DEAFEN,
                DatabaseUtils.CREATE_TABLE_LOG_MESSAGE_DELETE,
                DatabaseUtils.CREATE_TABLE_LOG_MESSAGE,
                DatabaseUtils.CREATE_TABLE_LOG_MESSAGE_UPDATE,
                DatabaseUtils.CREATE_TABLE_LOG_USER_ACTIVITY_START,
                DatabaseUtils.CREATE_TABLE_LOG_USER_ACTIVITY_END,
                DatabaseUtils.CREATE_TABLE_LOG_USER_UPDATE_GLOBAL_NAME,
            ).forEach { sql ->
                val statement = dbConnection.createStatement()
                statement.executeUpdate(sql)
                statement.close()
            }
        } catch(ex: SQLException) {
            dbConnection.close()
            throw ex
        }
    }

}