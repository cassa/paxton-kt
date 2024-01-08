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
import au.cassa.paxton.listener.ListenerManager
import au.cassa.paxton.util.ThreadUtils
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import net.dv8tion.jda.api.utils.cache.CacheFlag
import java.util.logging.Logger
import javax.security.auth.login.LoginException
import kotlin.system.exitProcess

object Paxton {

    init {
        // Change the logging format
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1\$tF %1\$tT %4\$s %5\$s%6\$s%n")
    }

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
        log.info(
            """
                
                 ____________________
                | Paxton-kt by CASSA |
                '--------------------'
                        \   ^__^
                         \  (oo)\_______
                            (__)\       )\/\
                                ||----w |
                                ||     ||
                                
                ... Paxton is starting up ...
            """.trimIndent()
        )

        // order of calls is important here
        ConfigManager.load()
        DatabaseManager.startup()
        loadShards()
        ListenerManager.load()
        log.info("Startup complete.")

        while (true) {
            log.info("Awaiting command... (Use 'help' for help, 'quit' to shutdown.)")
            print("$ ")
            val command: List<String> = readln().split(' ')
            when (command[0].lowercase()) {
                "quit", "q", "exit", "stop", "end" -> {
                    shutdown()
                }

                "help", "h", "commands", "cmds" -> {
                    log.info(
                        """
                            Available commands:
                             • help | h | commands | cmds
                               View available commands.
                               
                             • quit | q | exit | stop | end
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
        log.info("... Paxton is shutting down ...")
        ThreadUtils.asyncExecutor.shutdownNow()
        shardManager.shutdown()
        DatabaseManager.shutdown()
        log.info("Thank you and goodbye")
        exitProcess(0)
    }

    fun reload() {
        log.info("Reloading...")

        ConfigManager.load()

        log.info("Reloaded successfully.")
    }

    /**
     * Initialise JDA's shard management system.
     * This is where the real bot stuff begins.
     */
    private fun loadShards() {
        log.info("Loading shards...")
        try {
            shardManager = DefaultShardManagerBuilder
                .createDefault(SecretCfg.botToken())
                .enableIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .enableCache(CacheFlag.entries.toSet())
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("cassa.au"))
                .build()
            log.info("ShardManager built.")
        } catch (ex: LoginException) {
            log.severe("Unable to login; are you using a valid bot token and have an internet connection?")
            throw ex
        }
    }

}