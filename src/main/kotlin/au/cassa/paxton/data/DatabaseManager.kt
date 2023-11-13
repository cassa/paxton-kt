package au.cassa.paxton.data

import au.cassa.paxton.Paxton
import java.io.File
import java.io.FileNotFoundException
import java.sql.Connection
import java.util.*

object DatabaseManager {

    // SQLite: const val DB_RELATIVE_PATH = "sqlite.db"

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
        // todo mysql
        // SQLite: connection = DriverManager.getConnection("jdbc:sqlite:$DB_RELATIVE_PATH")
    }

    private fun createTables() {
        val statement = connection.createStatement()

        statement.executeUpdate(readSqlResourceFile("create_tables"))

        statement.close()
    }

    private fun readSqlResourceFile(
        name: String
    ): String {
        val resourcePath = "sql${File.separator}${name}.sql"

        val resource = Paxton.javaClass.getResource(resourcePath)
            ?: throw FileNotFoundException("Unable to load resource '${resourcePath}'")

        return resource.readText()
    }

}