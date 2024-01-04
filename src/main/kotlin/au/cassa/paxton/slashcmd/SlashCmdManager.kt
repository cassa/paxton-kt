package au.cassa.paxton.slashcmd

import au.cassa.paxton.Paxton
import au.cassa.paxton.slashcmd.impl.*

object SlashCmdManager {

    private val commands: LinkedHashSet<SlashCmd> = linkedSetOf(
        PaxtonSlashCmd,
        PingSlashCmd,
        ReloadSlashCmd,
        SendTemplateSlashCmd,
        UptimeSlashCmd,
    )

    fun load() {
        Paxton.log.info("Loading slash commands...")
        commands.forEach(SlashCmd::register)
        updateCommands()
        Paxton.log.info("Loaded slash commands.")
    }

    private fun updateCommands() {
        Paxton.log.info("Updating slash commands per guild...")
        Paxton.shardManager.guilds.forEach { guild ->
            Paxton.log.info("Updating slash commands for guild '${guild.name}' (id=${guild.id})...")
            guild.updateCommands().addCommands(commands.map { it.cmdData }).queue()
            Paxton.log.info("Updated ${commands.size} slash commands for guild.")
        }
        Paxton.log.info("Updated slash commands on ${Paxton.shardManager.guilds.size} guilds.")
    }
}