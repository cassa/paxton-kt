package au.cassa.paxton.config.impl

import au.cassa.paxton.config.type.YamlConfig

object SecretCfg : YamlConfig("secret") {

    const val UNDEFINED_VALUE = "UNDEFINED"

    fun botToken(): String {
        System.getenv("")

        val token = nodeIfNotEnv("bot-token")
        //val token = rootNode.node("bot-token").string!!

        if (token.equals(UNDEFINED_VALUE, ignoreCase = true)) {
            throw IllegalArgumentException("Bot token cannot be undefined in $relativePath")
        }

        return token
    }

    fun databaseUsername(): String {
        return nodeIfNotEnv("database", "username")
        //return rootNode.node("database", "username").string!!
    }

    fun databasePassword(): String {
        return nodeIfNotEnv("database", "password")
        //return rootNode.node("database", "password").string!!
    }

}
