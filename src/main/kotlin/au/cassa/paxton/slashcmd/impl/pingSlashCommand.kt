package au.cassa.paxton.slashcmd.impl

import au.cassa.paxton.slashcmd.SlashCmd
import au.cassa.paxton.slashcmd.SlashCmdUtils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.lang.Exception
import java.lang.management.ManagementFactory
import java.net.InetAddress
import java.time.LocalDate

class pingSlashCommand : SlashCmd(
    cmdData = Commands
        .slash("Bot Ping", "Skill issue if over >100ms.")
        .setDefaultPermissions(DefaultMemberPermissions.ENABLED)
) {

    override fun onInteraction(
        event: SlashCommandInteractionEvent
    ) {
        event
            .replyEmbeds(
                EmbedBuilder()
                    .setTitle("Ping")
                    .setDescription(
                        """
                        Ping - discord.com: ${ReachAddrTime("discord.com", 2000)} ms
                        
                        Ping - cassa.au: ${ReachAddrTime("cassa.au", 2000)} ms
                        
                        Ping - message: ${(System.currentTimeMillis() - event.timeCreated.toEpochSecond()) * 1000} ms
                        """.trimIndent()
                    )
                    .setColor(SlashCmdUtils.colorInfo)
                    .build()
            )
            .setEphemeral(true)
            .queue()
    }

    override fun onAutoComplete(event: CommandAutoCompleteInteractionEvent) {
        // intentionally blank: no suggestions to provide
    }

    private fun ReachAddrTime(ip: String, maxTime: Int): Long {

        try {
            val startTime: Long = System.currentTimeMillis()
            val address: InetAddress = InetAddress.getByName(ip)

            if (address.isReachable(maxTime)) return (System.currentTimeMillis() - startTime)
        }
        catch (e: Exception)
        {
            System.out.println(e)
        }

        return -1

    }

}