/*
Paxton is a Discord Bot to aid in managing the CASSA Discord Server.

Copyright © 2023  Lachlan Adamson

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package au.cassa.paxton

import au.cassa.paxton.config.ConfigManager
import au.cassa.paxton.config.impl.SecretCfg
import au.cassa.paxton.data.DatabaseManager
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import java.util.logging.Logger
import javax.security.auth.login.LoginException
import kotlin.system.exitProcess

object Paxton {

    // Neat console logging utility from Java.
    val log: Logger = Logger.getLogger("Paxton")

    // JDA's shard manager. Initialized @ loadShards.
    lateinit var shardManager: ShardManager
        private set

    /**
     * The JVM runs this function on the program's startup.
     */
    @JvmStatic
    fun main(args: Array<String>) {
        startup()
    }

    fun startup() {
        log.info("Starting up")

        // order of calls is important here
        ConfigManager.load()
        DatabaseManager.startup()
        loadShards()
        // note: listeners and commands are loaded in ReadyListener

        while (true) {
            log.info("Awaiting command... (Use 'help' for help, 'quit' to shutdown.)")
            val command: List<String> = readln().split(' ')
            when (command[0].lowercase()) {
                "quit" -> {
                    shutdown()
                }

                "help" -> {
                    log.info(
                        """
                            Available commands:
                             • help
                               View available commands.
                               
                             • quit
                               Makes the bot shut-down.
                        """.trimIndent()
                    )
                }

                else -> {
                    log.warning("Unknown command '${command[0]}'")
                }
            }
        }
    }

    fun shutdown() {
        log.info("Shutting down")
        shardManager.shutdown()
        DatabaseManager.shutdown()
        exitProcess(0)
    }

    /**
     * Initialise JDA's shard management system.
     * This is where the real bot stuff begins.
     */
    private fun loadShards() {
        try {
            shardManager = DefaultShardManagerBuilder
                .createDefault(SecretCfg.token())
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("cassa.au"))
                .enableIntents(GatewayIntent.values().toSet()) // we want ALL the intents! :)
                .build()
        } catch (ex: LoginException) {
            log.severe("Unable to login; are you using a valid bot token and have an internet connection?")
            throw ex
        }
    }

}