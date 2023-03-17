package au.cassa.paxton.command

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import java.awt.Color

object CommandUtils {

    val defaultPermission = Permission.VIEW_CHANNEL
    val colorSevere = Color(255, 126, 126)
    val colorWarning = Color(255, 218, 126)
    val colorInfo = Color(126, 178, 255)
    val colorSuccess = Color(139, 255, 126)

    fun replyNoPermissionToSlashCmd(
        event: SlashCommandInteractionEvent
    ) {
        event
            .replyEmbeds(
                EmbedBuilder()
                    .setTitle("No Permission")
                    .setDescription(
                        """
                        We're sorry, but you don't have access to that.
                        Please contact a CASSA Tech Admin if you believe this is in error. 
                        """.trimIndent()
                    )
                    .setColor(colorSevere)
                    .build()
            )
            .setEphemeral(true)
            .queue()
    }

}