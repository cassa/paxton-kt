package au.cassa.paxton.listener.impl

import au.cassa.paxton.userlog.UserlogManager
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateAvatarEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*

object GuildMemberUpdateAvatarListener : ListenerAdapter() {

    override fun onGuildMemberUpdateAvatar(
        event: GuildMemberUpdateAvatarEvent
    ) {
        UserlogManager.insertGuildMemberUpdateAvatarRecord(
            disUserId = event.member.idLong,
            username = event.member.user.name,
            timestamp = Date(),
            guildId = event.guild.idLong,
            oldAvatarUrl = event.oldAvatarUrl,
            newAvatarUrl = event.newAvatarUrl
        )
    }

}