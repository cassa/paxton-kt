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

import au.cassa.paxton.config.impl.Secret
import au.cassa.paxton.listener.TestListener
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import java.util.logging.Logger
import javax.security.auth.login.LoginException

object Paxton {

    @Suppress("MemberVisibilityCanBePrivate")
    val logger: Logger = Logger.getLogger("Paxton")

    @Suppress("MemberVisibilityCanBePrivate")
    lateinit var shardManager: ShardManager

    @JvmStatic
    fun main(args: Array<String>) {
        build()
    }

    private fun build() {
        try {
            shardManager = DefaultShardManagerBuilder
                .createDefault(Secret.token())
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("cassa.au"))
                .addEventListeners(
                    TestListener
                )
                //.enableIntents() //TODO add required gateway intents.
                .build()
        } catch(ex: LoginException) {
            logger.severe("Unable to login; possibly using invalid bot token.")
        }
    }

}