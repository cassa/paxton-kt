package au.cassa.paxton.slashcmd

import au.cassa.paxton.Paxton
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

abstract class SlashCmd(
    val cmdData: SlashCommandData,
) {

    fun load() {
        // Each slash command registers its own event listener.
        Paxton.shardManager.addEventListener(
            // Create a new ListenerAdapter object which contains a slash command listener.
            object : ListenerAdapter() {
                override fun onSlashCommandInteraction(
                    event: SlashCommandInteractionEvent
                ) {
                    // If the event is already acknowledged, return.
                    if(event.isAcknowledged) return

                    // If the user types in a command that isn't this one, return.
                    if(!event.name.equals(cmdData.name, ignoreCase = true)) return

                    // Okay, all checks have passed :) run!
                    onInteraction(event)
                }

                override fun onCommandAutoCompleteInteraction(
                    event: CommandAutoCompleteInteractionEvent
                ) {
                    // If the event is already acknowledged, return.
                    if(event.isAcknowledged) return

                    // If the user types in a command that isn't this one, return.
                    if(!event.name.equals(cmdData.name, ignoreCase = true)) return

                    // Okay, all checks have passed :) run!
                    onAutoComplete(event)
                }
            }
        )

        Paxton.shardManager.guilds.forEach { guild -> guild.upsertCommand(cmdData)}

        Paxton.log.info("Loaded slash command '${cmdData.name}'")
    }

    abstract fun onInteraction(event: SlashCommandInteractionEvent)

    abstract fun onAutoComplete(event: CommandAutoCompleteInteractionEvent)

}