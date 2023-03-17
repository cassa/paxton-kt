package au.cassa.paxton.command

import au.cassa.paxton.command.impl.PaxtonCommand

object CommandManager {

    private val commands = linkedSetOf<SlashCommand>(
        PaxtonCommand
    )

    fun load() {
        commands.forEach(SlashCommand::load)
    }
}