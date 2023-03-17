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

import au.cassa.paxton.command.CommandManager
import au.cassa.paxton.config.ConfigManager
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import java.util.logging.Logger
import javax.security.auth.login.LoginException

object Paxton {

    // Neat console logging utility from Java.
    val logger: Logger = Logger.getLogger("Paxton")

    // JDA's shard manager. Initialized @ loadShards.
    lateinit var shardManager: ShardManager

    // Token of the bot account. Initialized @ loadEnv.
    lateinit var token: String

    /**
     * The JVM runs this function on the program's startup.
     */
    @JvmStatic
    fun main(args: Array<String>) {
        // Please try to keep the correct order of method calls.

        loadEnv()
        loadShards()
        ConfigManager.load()
        CommandManager.load()
    }

    /**
     * Load miscellaneous environment variables.
     */
    private fun loadEnv() {
        // Various environment variables. Load them into variables so they're easy to access.

        token = System.getProperty("PAXTON_TOKEN")
    }

    /**
     * Initialise JDA's shard management system.
     * This is where the real bot stuff begins.
     */
    private fun loadShards() {
        try {
            shardManager = DefaultShardManagerBuilder
                .createDefault(token)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("cassa.au"))
                .enableIntents(
                    // just load everything into here
                    GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.GUILD_INVITES,
                    GatewayIntent.GUILD_MESSAGE_REACTIONS,
                    GatewayIntent.GUILD_MESSAGE_TYPING,
                    GatewayIntent.GUILD_MODERATION,
                    GatewayIntent.GUILD_PRESENCES,
                    GatewayIntent.GUILD_VOICE_STATES,
                    GatewayIntent.GUILD_WEBHOOKS,
                    GatewayIntent.SCHEDULED_EVENTS,
                    GatewayIntent.MESSAGE_CONTENT
                )
                .build()
        } catch(ex: LoginException) {
            logger.severe("Unable to login; possibly using invalid bot token.")
        }
    }

}