package au.cassa.paxton.config

import au.cassa.paxton.Paxton.log
import au.cassa.paxton.config.impl.SecretCfg
import au.cassa.paxton.config.impl.SettingsCfg

object ConfigManager {

    private val configs: LinkedHashSet<Config> = linkedSetOf(
        SecretCfg,
        SettingsCfg,
    )

    fun load() {
        log.info("Loading configs...")
        configs.forEach(Config::load)
    }

}