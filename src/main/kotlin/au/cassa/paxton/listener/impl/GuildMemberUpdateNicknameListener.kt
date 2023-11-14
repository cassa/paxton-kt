package au.cassa.paxton.listener.impl

import au.cassa.paxton.userlog.UserlogManager
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*

object GuildMemberUpdateNicknameListener : ListenerAdapter() {

    override fun onGuildMemberUpdateNickname(event: GuildMemberUpdateNicknameEvent) {
        UserlogManager.insertGuildMemberUpdateNicknameRecord(
            disUserId = event.member.idLong,
            username = event.member.user.name,
            timestamp = Date(),
            guildId = event.guild.idLong,
            oldNickname = event.oldNickname,
            newNickname = event.newNickname
        )
    }

}