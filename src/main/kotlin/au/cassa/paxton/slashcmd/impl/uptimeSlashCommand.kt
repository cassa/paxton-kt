package au.cassa.paxton.slashcmd.impl

import au.cassa.paxton.slashcmd.SlashCmd
import au.cassa.paxton.slashcmd.SlashCmdUtils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.lang.management.ManagementFactory

object UptimeSlashCommand : SlashCmd(
    cmdData = Commands
        .slash("uptime", "View the uptime of CASSA's discord bot!")
        .setDefaultPermissions(DefaultMemberPermissions.ENABLED)
) {

    override fun onInteraction(
        event: SlashCommandInteractionEvent
    ) {
        event
            .replyEmbeds(
                EmbedBuilder()
                    .setTitle("Uptime")
                    .setDescription("JVM uptime: ${ManagementFactory.getRuntimeMXBean().uptime}ms")
                    .setColor(SlashCmdUtils.colorInfo)
                    .build()
            )
            .setEphemeral(true)
            .queue()
    }

    override fun onAutoComplete(event: CommandAutoCompleteInteractionEvent) {
        // intentionally blank: no suggestions to provide
    }

}