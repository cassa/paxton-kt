package au.cassa.paxton.slashcmd

import au.cassa.paxton.Paxton
import au.cassa.paxton.slashcmd.impl.PaxtonSlashCmd
import au.cassa.paxton.slashcmd.impl.SendTemplateSlashCmd

object SlashCmdManager {

    private val commands: LinkedHashSet<SlashCmd> = linkedSetOf(
        PaxtonSlashCmd,
        SendTemplateSlashCmd
    )

    fun load() {
        Paxton.log.info("Loading slash commands")
        commands.forEach(SlashCmd::load)
    }
}