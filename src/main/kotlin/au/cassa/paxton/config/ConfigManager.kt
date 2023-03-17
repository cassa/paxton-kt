package au.cassa.paxton.config

import au.cassa.paxton.config.impl.Settings

object ConfigManager {

    private val configs = linkedSetOf<Config>(
        Settings
    )

    fun load() {
        configs.forEach(Config::load)
    }

}