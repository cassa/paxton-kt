package au.cassa.paxton.command

import au.cassa.paxton.command.impl.PaxtonCmd
import au.cassa.paxton.command.impl.SendTemplateCmd

object CommandManager {

    private val commands: LinkedHashSet<SlashCommand> = linkedSetOf(
        PaxtonCmd,
        SendTemplateCmd
    )

    fun load() {
        commands.forEach(SlashCommand::load)
    }
}