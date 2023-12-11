package au.cassa.paxton.slashcmd.impl

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

object PingSlashCommand : SlashCmd(
    cmdData = Commands
        .slash("ping", "Check the bot's ping. Skill issue if over >100ms.")
        .setDefaultPermissions(DefaultMemberPermissions.ENABLED)
) {

    const val UNABLE_TO_REACH = "Unable to reach; timed out"

    override fun onInteraction(
        event: SlashCommandInteractionEvent
    ) {
        // each reachAddr call will take up to 2 seconds (usually very quick), so we have to run this async
        // and defer the reply

        event.deferReply(true).queue()

        ThreadUtils.executorService.run {
            val strBuilder = StringBuilder()

            val discordLatency: Long? = reachAddrTime("discord.com")
            strBuilder.append(" • Latency [Paxton -> discord.com]: ")
            if(discordLatency == null) {
                strBuilder.append(UNABLE_TO_REACH)
            } else {
                strBuilder.append("${discordLatency}ms")
            }
            strBuilder.append("\n\n")

            val cassaLatency: Long? = reachAddrTime("cassa.au")
            strBuilder.append(" • Latency [Paxton -> cassa.au]: ")
            if(cassaLatency == null) {
                strBuilder.append(UNABLE_TO_REACH)
            } else {
                strBuilder.append("${cassaLatency}ms")
            }
            strBuilder.append("\n\n")

            val timeTaken: Long = currentTimeMillis() - (event.timeCreated.toEpochSecond() * 1000)
            strBuilder.append("Time taken: ${timeTaken}ms")

            event
                .replyEmbeds(
                    EmbedBuilder()
                        .setTitle("Ping pong!")
                        .setDescription(strBuilder.toString())
                        .setColor(SlashCmdUtils.colorInfo)
                        .build()
                )
                .setEphemeral(true)
                .queue()
        }
    }

    override fun onAutoComplete(event: CommandAutoCompleteInteractionEvent) {
        // intentionally blank: no suggestions to provide
    }

    private fun reachAddrTime(ip: String, maxTime: Int = 2000): Long? {

        try {
            val startTime: Long = currentTimeMillis()
            val address: InetAddress = InetAddress.getByName(ip)

            if (address.isReachable(maxTime))
                return (currentTimeMillis() - startTime)
        } catch (ex: Exception) {
            println("Unable to run reachAddrTime with ip=${ip}, maxTime=${maxTime}; is the website down? Stack trace:")
            ex.printStackTrace()
        }

        return null

    }

}