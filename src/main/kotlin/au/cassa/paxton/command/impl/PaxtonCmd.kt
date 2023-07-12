package au.cassa.paxton.command.impl

import au.cassa.paxton.command.CommandUtils
import au.cassa.paxton.command.SlashCommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.build.Commands

object PaxtonCmd : SlashCommand(
    cmdData = Commands
        .slash("paxton", "Learn more about Paxton, CASSA's Discord bot")
        .setDefaultPermissions(DefaultMemberPermissions.ENABLED)
) {

    override fun onInteraction(
        event: SlashCommandInteractionEvent
    ) {
        event
            .replyEmbeds(
                EmbedBuilder()
                    .setTitle("Paxton")
                    .setDescription(
                        """
                        Paxton is CASSA's own Discord bot! It assists the executive team in managing the CASSA Discord server.
                        
                        Our bot is written in Kotlin (JVM) and uses the JDA library to interface with Discord. 
                        
                        Paxton is libre software (GPLv3); source code is available on our [GitHub repository](https://github.com/cassa/paxton-kt).
                        
                        If you have any queries or have an interest in contributing, contact CASSA's Tech Admin. :)
                        """.trimIndent()
                    )
                    .setColor(CommandUtils.colorInfo)
                    .build()
            )
            .setEphemeral(true)
            .queue()
    }

    override fun onAutoComplete(event: CommandAutoCompleteInteractionEvent) {
        // intentionally blank: no suggestions to provide
    }

}