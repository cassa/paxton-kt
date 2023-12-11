package au.cassa.paxton.config.impl

import au.cassa.paxton.config.type.YamlConfig

object SecretCfg : YamlConfig("secret") {

    const val UNDEFINED_VALUE = "UNDEFINED"

    fun botToken(): String {
        val token = rootNode.node("bot_token").string!!

        if (token.equals(UNDEFINED_VALUE, ignoreCase = true)) {
            throw IllegalArgumentException("Bot token cannot be undefined in $relativePath")
        }

        return token
    }

    fun databaseUsername(): String {
        return rootNode.node("database", "username").string!!
    }

    fun databasePassword(): String {
        return rootNode.node("database", "password").string!!
    }

}
