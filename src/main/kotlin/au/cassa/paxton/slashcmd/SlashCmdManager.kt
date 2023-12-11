package au.cassa.paxton.slashcmd

import au.cassa.paxton.Paxton
import au.cassa.paxton.slashcmd.impl.PaxtonSlashCmd
import au.cassa.paxton.slashcmd.impl.PingSlashCommand
import au.cassa.paxton.slashcmd.impl.SendTemplateSlashCmd
import au.cassa.paxton.slashcmd.impl.UptimeSlashCommand

object SlashCmdManager {

    private val commands: LinkedHashSet<SlashCmd> = linkedSetOf(
        PaxtonSlashCmd,
        PingSlashCommand,
        SendTemplateSlashCmd,
        UptimeSlashCommand,
    )

    fun load() {
        Paxton.log.info("Loading slash commands...")
        commands.forEach(SlashCmd::load)
        Paxton.log.info("Loaded slash commands.")
    }
}