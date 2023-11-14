package au.cassa.paxton.listener.impl

import au.cassa.paxton.userlog.UserlogManager
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*

object GuildMemberRemoveListener : ListenerAdapter() {

    override fun onGuildMemberRemove(event: GuildMemberRemoveEvent) {
        UserlogManager.insertGuildMemberRemoveRecord(
            disUserId = event.user.idLong,
            username = event.user.name,
            timestamp = Date(),
            guildId = event.guild.idLong,
            displayName = event.user.effectiveName,
            userCount = event.guild.memberCount
        )
    }

}