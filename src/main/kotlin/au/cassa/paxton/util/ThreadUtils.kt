package au.cassa.paxton.util

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object ThreadUtils {

    val executorService: ExecutorService = Executors.newCachedThreadPool()

}