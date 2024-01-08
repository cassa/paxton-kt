package au.cassa.paxton.slashcmd.impl

import au.cassa.paxton.Paxton
import au.cassa.paxton.slashcmd.SlashCmd
import au.cassa.paxton.slashcmd.SlashCmdUtils
import au.cassa.paxton.util.ThreadUtils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.build.Commands

object ReloadSlashCmd : SlashCmd(
    cmdData = Commands
        .slash("reload", "Reload the bot's configuration.")
        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL))
) {

    override fun onInteraction(
        event: SlashCommandInteractionEvent
    ) {
        event.deferReply(true).queue()

        ThreadUtils.asyncExecutor.submit {
            try {
                Paxton.reload()
            } catch(ex: Exception) {
                // caught exception - inform user and log it, then return out of the cmd
                event
                    .interaction
                    .hook
                    .editOriginalEmbeds(
                        EmbedBuilder()
                            .setTitle("Reload Failed!")
                            .setDescription("Caught exception: ${ex.message}")
                            .addField("Stack Trace:", ex.stackTraceToString(), false)
                            .setColor(SlashCmdUtils.colorSevere)
                            .build()
                    )
                    .queue()

                Paxton.log.severe("Encountered exception whilst reloading Paxton via slash command. Stack trace:")
                ex.printStackTrace()
                return@submit
            }

            // reload successful - update reply with success msg
            event
                .interaction
                .hook
                .editOriginalEmbeds(
                    EmbedBuilder()
                        .setTitle("Reloaded!")
                        .setDescription(
                            """
                                Paxton reloaded successfully.
                                
                                *Note: The reload slash command only updates the config loaded in memory, it does not reconnect the database or shards, which would require a restart.*
                            """.trimIndent()
                        )
                        .setColor(SlashCmdUtils.colorInfo)
                        .build()
                )
                .queue()
        }
    }

    override fun onAutoComplete(event: CommandAutoCompleteInteractionEvent) {
        // intentionally blank: no suggestions to provide
    }

}