package au.cassa.paxton.data

import au.cassa.paxton.Paxton.log
import au.cassa.paxton.config.impl.SecretCfg
import au.cassa.paxton.config.impl.SettingsCfg
import au.cassa.paxton.util.DatabaseUtils
import au.cassa.paxton.util.ThreadUtils
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.concurrent.TimeUnit

object DatabaseManager {

    lateinit var dbConnection: Connection
        private set

    fun startup() {
        log.info("Starting database manager...")
        connect()
        createTables()
        scheduleUserlogAutoPurging()
        log.info("Started database manager successfully.")
    }

    fun shutdown() {
        log.info("Shutting down database manager...")
        dbConnection.close()
        log.info("Shut-down database manager successfully.")
    }

    private fun connect() {
        log.info("Connecting to database...")

        val address: String = SettingsCfg.databaseAddress()
        val port: String = SettingsCfg.databasePort()
        val dbName: String = SettingsCfg.databaseName()
        val username: String = SecretCfg.databaseUsername()
        val password: String = SecretCfg.databasePassword()

        // Force include the driver in shading
        com.mysql.jdbc.Driver::class.simpleName;
        dbConnection = DriverManager.getConnection("jdbc:mysql://$address:$port/$dbName", username, password)
        log.info("Database connection established.")
    }

    private fun createTables() {
        log.info("Running DB table creation statements...")
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
            log.info("DB tables created (or already exist).")
        } catch(ex: SQLException) {
            dbConnection.close()
            throw ex
        }
    }

    fun scheduleUserlogAutoPurging() {
        log.info("Starting userlog auto-purge scheduler.")
        ThreadUtils.scheduledExecutor.scheduleAtFixedRate(
            {
                purgeOldUserlogs()
            },
            0,
            30,
            TimeUnit.DAYS
        )
    }

    fun purgeOldUserlogs() {
        log.info("Purging old userlogs...")
        try {
            with(dbConnection.createStatement()) {
                log.fine("Purge: Created statement.")
                DatabaseUtils.AUTO_PURGE_OLD_RECORDS.forEach { sql ->
                    log.fine("Purge: Running SQL: $sql")
                    executeUpdate(sql)
                }
                log.fine("Purge: Done, closing statement.")
                close()
            }
        } catch(ex: Exception) {
            log.severe("Unable to purge old userlogs! Stack trace:")
            ex.printStackTrace()
        }
        log.info("Finished purging userlogs.")
    }

}