package au.cassa.paxton.util

import java.util.concurrent.ExecutorService
import java.util.concurrent.ForkJoinPool

object ThreadUtils {

    val executorService: ExecutorService = ForkJoinPool.commonPool()

}