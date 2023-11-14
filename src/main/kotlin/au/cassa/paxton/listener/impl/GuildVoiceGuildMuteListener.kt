package au.cassa.paxton.listener.impl

import au.cassa.paxton.userlog.UserlogManager
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildMuteEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*

object GuildVoiceGuildMuteListener : ListenerAdapter() {

    override fun onGuildVoiceGuildMute(event: GuildVoiceGuildMuteEvent) {
        UserlogManager.insertGuildVoiceGuildMuteRecord(
            disUserId = event.member.idLong,
            username = event.member.user.name,
            timestamp = Date(),
            guildId = event.guild.idLong,
            newState = event.isGuildMuted,
        )
    }

}