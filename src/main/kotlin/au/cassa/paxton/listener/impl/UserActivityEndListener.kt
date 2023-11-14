package au.cassa.paxton.listener.impl

import au.cassa.paxton.userlog.UserlogManager
import net.dv8tion.jda.api.events.user.UserActivityEndEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*

object UserActivityEndListener : ListenerAdapter() {

    override fun onUserActivityEnd(event: UserActivityEndEvent) {
        UserlogManager.insertUserActivityEndRecord(
            disUserId = event.user.idLong,
            username = event.user.name,
            timestamp = Date(),
            type = event.oldActivity.type.name,
            name = event.oldActivity.name,
            url = event.oldActivity.url,
            isRichPresence = event.oldActivity.isRich,
            emojiName = event.oldActivity.emoji?.name
        )
    }

}