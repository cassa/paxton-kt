package au.cassa.paxton.config.impl

import au.cassa.paxton.config.type.JsonConfig

object SecretCfg : JsonConfig("secret") {
    fun token(): String {
        val token = rootNode.node("bot_token").string!!

        if (token == "UNDEFINED") {
            throw IllegalArgumentException("Bot token cannot be undefined in $relativePath")
        }

        return token
    }
}
