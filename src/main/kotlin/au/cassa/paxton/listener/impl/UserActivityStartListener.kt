package au.cassa.paxton.listener.impl

import au.cassa.paxton.userlog.UserlogManager
import net.dv8tion.jda.api.events.user.UserActivityStartEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*

object UserActivityStartListener : ListenerAdapter() {

    override fun onUserActivityStart(event: UserActivityStartEvent) {
        UserlogManager.insertUserActivityStartRecord(
            disUserId = event.user.idLong,
            username = event.user.name,
            timestamp = Date(),
            type = event.newActivity.type.name,
            name = event.newActivity.name,
            url = event.newActivity.url,
            isRichPresence = event.newActivity.isRich,
            emojiName = event.newActivity.emoji?.name
        )
    }

}