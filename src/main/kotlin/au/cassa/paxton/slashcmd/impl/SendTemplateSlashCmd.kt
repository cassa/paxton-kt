package au.cassa.paxton.slashcmd.impl

import au.cassa.paxton.slashcmd.SlashCmd
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands

/**
 * Posts a user-provided message template in the channel the command is ran in.
 */
object SendTemplateSlashCmd : SlashCmd(
    cmdData = Commands
        .slash("send-template", "Send message template")
        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL))
        .addOption(OptionType.STRING, "id", "Message template ID", true, true)
) {

    override fun onInteraction(
        event: SlashCommandInteractionEvent
    ) {
        val templateId: String = event.getOption("name")!!.asString

        //TODO if id is not a valid message template ID, notify user ephemerally and return

        //TODO get message contents and send it to the channel (non-ephemerally).
    }

    override fun onAutoComplete(event: CommandAutoCompleteInteractionEvent) {
        when (event.focusedOption.name) {
            "id" -> {
                val availableIds: Collection<String> = listOf() // TODO populate list of available message template IDs
                event.replyChoiceStrings(availableIds.filter { it.contains(event.focusedOption.value) }).queue()
            }
            else -> {
                // intentionally blank: no suggestions available
            }
        }
    }

}