package au.cassa.paxton.data

import au.cassa.paxton.Paxton
import java.io.File
import java.io.FileNotFoundException
import java.sql.Connection
import java.util.*

object DatabaseManager {

    lateinit var connection: Connection
        private set

    fun startup() {
        connect()
        createTables()
    }

    fun shutdown() {
        Objects.requireNonNull(connection, "connection")
        connection.close()
    }

    private fun connect() {
        // todo connect to mysql
        TODO("Not yet implemented")
    }

    private fun createTables() {
        with(connection.createStatement()) {
            executeUpdate(readSqlResourceFile("create_tables"))
            close()
        }
    }

    @Suppress("SameParameterValue")
    private fun readSqlResourceFile(
        name: String
    ): String {
        val resourcePath = "sql${File.separator}${name}.sql"

        val resource = Paxton.javaClass.getResource(resourcePath)
            ?: throw FileNotFoundException("Unable to load resource '${resourcePath}'")

        return resource.readText()
    }

}