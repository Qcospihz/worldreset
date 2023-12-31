package cc.mewcraft.worldreset.manager

import cc.mewcraft.worldreset.message.SlavePluginMessenger
import cc.mewcraft.worldreset.util.throwUnsupportedException
import me.lucko.helper.cache.Expiring
import java.util.concurrent.TimeUnit

class RemoteServerLocks(
    private val messenger: SlavePluginMessenger,
) : ServerLocks {
    private val cachedStatus = Expiring.suppliedBy({
        messenger.queryServerLock()
    }, 1, TimeUnit.MINUTES)

    override fun lock(): Unit =
        throwUnsupportedException()

    override fun unlock(): Unit =
        throwUnsupportedException()

    override fun setLock(status: Boolean): Unit =
        throwUnsupportedException()

    override fun isLocked(): Boolean {
        val promise = cachedStatus.get()
        return if (promise.isDone) {
            promise.join().lockStatus
        } else {
            // If the result is not yet calculated,
            // we return `true` for temporary value.
            // Returning `true` should be safer than `false`.
            true
        }
    }
}