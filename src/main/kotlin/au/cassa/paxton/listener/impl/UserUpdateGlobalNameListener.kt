package au.cassa.paxton.listener.impl

import au.cassa.paxton.userlog.UserlogManager
import net.dv8tion.jda.api.events.user.update.UserUpdateGlobalNameEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*

object UserUpdateGlobalNameListener : ListenerAdapter() {

    override fun onUserUpdateGlobalName(event: UserUpdateGlobalNameEvent) {
        UserlogManager.insertUserUpdateGlobalNameRecord(
            disUserId = event.user.idLong,
            username = event.user.name,
            timestamp = Date(),
            oldName = event.oldGlobalName,
            newName = event.newGlobalName
        )
    }

}