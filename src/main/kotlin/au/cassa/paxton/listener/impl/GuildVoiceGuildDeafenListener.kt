package au.cassa.paxton.listener.impl

import au.cassa.paxton.userlog.UserlogManager
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildDeafenEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*

object GuildVoiceGuildDeafenListener : ListenerAdapter() {

    override fun onGuildVoiceGuildDeafen(event: GuildVoiceGuildDeafenEvent) {
        UserlogManager.insertGuildVoiceGuildDeafenRecord(
            disUserId = event.member.idLong,
            username = event.member.user.name,
            timestamp = Date(),
            guildId = event.guild.idLong,
            newState = event.isGuildDeafened,
        )
    }

}