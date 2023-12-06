package au.cassa.paxton.listener

import au.cassa.paxton.Paxton
import au.cassa.paxton.listener.impl.*
import net.dv8tion.jda.api.hooks.ListenerAdapter

object ListenerManager {

    val listeners: Collection<ListenerAdapter> = linkedSetOf(
        GuildMemberJoinListener,
        GuildMemberRemoveListener,
        GuildMemberUpdateAvatarListener,
        GuildMemberUpdateNicknameListener,
        GuildVoiceGuildDeafenListener,
        GuildVoiceGuildMuteListener,
        MessageDeleteRecordListener,
        MessageReceivedListener,
        MessageUpdateListener,
        ReadyListener,
        UserActivityEndListener,
        UserActivityStartListener,
        UserUpdateGlobalNameListener,
        UserUpdateNameListener,
    )

    fun load() {
        Paxton.log.info("Loading listeners...")
        listeners.forEach {
            Paxton.log.info("Loading listener ${it.javaClass.simpleName}...")
            Paxton.shardManager.addEventListener(it)
        }
        Paxton.log.info("Listeners loaded.")
    }

}