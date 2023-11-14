package au.cassa.paxton.listener.impl

import au.cassa.paxton.userlog.UserlogManager
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*

object GuildMemberJoinListener : ListenerAdapter() {

    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        UserlogManager.insertGuildMemberJoinRecord(
            disUserId = event.user.idLong,
            username = event.user.name,
            timestamp = Date(),
            guildId = event.guild.idLong,
            displayName = event.user.effectiveName,
            userCount = event.guild.memberCount
        )
    }

}