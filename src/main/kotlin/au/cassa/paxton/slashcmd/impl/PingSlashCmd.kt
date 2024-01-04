package au.cassa.paxton.slashcmd.impl

import au.cassa.paxton.Paxton
import au.cassa.paxton.slashcmd.SlashCmd
import au.cassa.paxton.slashcmd.SlashCmdUtils
import au.cassa.paxton.util.ThreadUtils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.lang.System.currentTimeMillis
import java.net.InetAddress

object PingSlashCmd : SlashCmd(
    cmdData = Commands
        .slash("ping", "Check the bot's ping. Skill issue if over >100ms.")
        .setDefaultPermissions(DefaultMemberPermissions.ENABLED)
) {

    const val UNABLE_TO_REACH = "Unable to reach; timed out"

    override fun onInteraction(
        event: SlashCommandInteractionEvent
    ) {
        // each reachAddr call will take up to 5 seconds (usually very quick), so we have to run this async
        // and defer the reply

        event.deferReply(true).queue()

        ThreadUtils.asyncExecutor.submit {
            val timeTaken: Long = currentTimeMillis() - (event.timeCreated.toEpochSecond() * 1000)
            val strBuilder = StringBuilder("Time taken: ${timeTaken}ms")

            val localhostLatency: Long? = reachAddrTime("127.0.0.1")
            strBuilder.append("\n\n • Latency [Paxton -> Localhost 127.0.0.1]: ")
            if(localhostLatency == null) {
                strBuilder.append(UNABLE_TO_REACH)
            } else {
                strBuilder.append("${localhostLatency}ms")
            }

            val cloudflareLatency: Long? = reachAddrTime("1.1.1.1")
            strBuilder.append("\n\n • Latency [Paxton -> Cloudflare DNS 1.1.1.1]: ")
            if(cloudflareLatency == null) {
                strBuilder.append(UNABLE_TO_REACH)
            } else {
                strBuilder.append("${cloudflareLatency}ms")
            }

            val discordLatency: Long? = reachAddrTime("discord.com")
            strBuilder.append("\n\n • Latency [Paxton -> discord.com]: ")
            if(discordLatency == null) {
                strBuilder.append(UNABLE_TO_REACH)
            } else {
                strBuilder.append("${discordLatency}ms")
            }

            val cassaLatency: Long? = reachAddrTime("cassa.au")
            strBuilder.append("\n\n • Latency [Paxton -> cassa.au]: ")
            if(cassaLatency == null) {
                strBuilder.append(UNABLE_TO_REACH)
            } else {
                strBuilder.append("${cassaLatency}ms")
            }

            event.interaction.hook
                .editOriginalEmbeds(EmbedBuilder()
                    .setTitle("Ping pong!")
                    .setDescription(strBuilder.toString())
                    .setColor(SlashCmdUtils.colorInfo)
                    .build()
                )
                .queue()
        }
    }

    override fun onAutoComplete(event: CommandAutoCompleteInteractionEvent) {
        // intentionally blank: no suggestions to provide
    }

    private fun reachAddrTime(host: String, maxTime: Int = 2000): Long? {

        try {
            val startTime: Long = currentTimeMillis()
            val address = InetAddress.getByName(host)
            Paxton.log.info("Pinging host=${host}, hostAddress=${address.hostAddress}")

            if (address.isReachable(maxTime))
                return currentTimeMillis() - startTime
        } catch (ex: Exception) {
            println("Unable to run reachAddrTime with host $host in ${maxTime}ms; is the website down? Stack trace:")
            ex.printStackTrace()
        }


        return null

    }

}