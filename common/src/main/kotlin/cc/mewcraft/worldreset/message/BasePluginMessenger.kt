package cc.mewcraft.worldreset.message

import me.lucko.helper.messaging.Messenger
import me.lucko.helper.messaging.conversation.ConversationChannel
import me.lucko.helper.terminable.Terminable

/**
 * Base messenger.
 */
sealed class BasePluginMessenger(
    messenger: Messenger,
) : Terminable {
    protected val scheduleChannel: ConversationChannel<GetScheduleRequest, GetScheduleResponse> = messenger.getConversationChannel(
        "worldreset-schedule", GetScheduleRequest::class.java, GetScheduleResponse::class.java
    )
    protected val serverLockChannel: ConversationChannel<QueryServerLockRequest, QueryServerLockResponse> = messenger.getConversationChannel(
        "worldreset-server-lock", QueryServerLockRequest::class.java, QueryServerLockResponse::class.java
    )

    override fun close() {
        scheduleChannel.close()
        serverLockChannel.close()
    }
}