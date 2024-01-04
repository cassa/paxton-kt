package au.cassa.paxton.util

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ScheduledExecutorService

object ThreadUtils {

    val asyncExecutor: ExecutorService = ForkJoinPool.commonPool()

    val scheduledExecutor: ScheduledExecutorService = Executors.newScheduledThreadPool(2)

}