package au.cassa.paxton.config.impl

import au.cassa.paxton.config.type.YamlConfig

object SettingsCfg : YamlConfig("settings") {

    fun databaseName(): String {
        return nodeIfNotEnv("database", "name")
        //return rootNode.node("database", "name").string!!
    }

    fun databaseAddress(): String {
        return nodeIfNotEnv("database", "address")
        //return rootNode.node("database", "address").string!!
    }

    fun databasePort(): String {
        return nodeIfNotEnv("database", "port")
        //return rootNode.node("database", "port").string!!
    }

}
