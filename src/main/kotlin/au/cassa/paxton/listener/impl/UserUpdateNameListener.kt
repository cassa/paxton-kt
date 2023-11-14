package au.cassa.paxton.listener.impl

import au.cassa.paxton.userlog.UserlogManager
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*

object UserUpdateNameListener : ListenerAdapter() {

    override fun onUserUpdateName(
        event: UserUpdateNameEvent
    ) {
        UserlogManager.insertUserUpdateNameRecord(
            disUserId = event.user.idLong,
            username = event.user.name,
            timestamp = Date(),
            oldName = event.oldName,
            newName = event.newName
        )
    }

}