package au.cassa.paxton.config

import au.cassa.paxton.config.impl.DataCfg
import au.cassa.paxton.config.impl.SettingsCfg

object ConfigManager {

    private val configs: LinkedHashSet<Config> = linkedSetOf(
        DataCfg,
        SettingsCfg,
    )

    fun load() {
        configs.forEach(Config::load)
    }

}