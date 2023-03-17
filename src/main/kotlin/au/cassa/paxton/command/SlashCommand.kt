package au.cassa.paxton.command

import au.cassa.paxton.Paxton
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.internal.interactions.CommandDataImpl

abstract class SlashCommand(
    val id: String,
    val description: String,
    val basePerm: Permission = CommandUtils.defaultPermission
) {

    val cmdData = CommandDataImpl(id, description)

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

                    // Split the command string by each space so we have each argument separated
                    // from the full command string.
                    val cmdSplitByArgs = event.commandString.split(" ")

                    // If the user types in a command that isn't this one, return.
                    if(!cmdSplitByArgs.first().startsWith("/$id")) return

                    // If JDA has a member object for this event + the member *doesn't* have
                    // the required base permission for this slash command, let em know + return.
                    if(event.member != null && !event.member!!.hasPermission(basePerm)) {
                        CommandUtils.replyNoPermissionToSlashCmd(event)
                        return
                    }

                    // Okay, all checks have passed :) run!
                    run(event)
                }
            }
        )

        Paxton.shardManager.guilds.forEach { guild -> guild.upsertCommand(cmdData)}
    }

    abstract fun run(event: SlashCommandInteractionEvent)

}