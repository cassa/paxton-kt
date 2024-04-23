package au.cassa.paxton.config

import org.spongepowered.configurate.CommentedConfigurationNode

abstract class Config(
    val id: String
) {

    lateinit var rootNode: CommentedConfigurationNode
        protected set

    abstract fun load()

    abstract fun save()

    abstract fun saveIfNotExists()

    abstract fun exists(): Boolean

    fun nodeIfNotEnv(vararg path: String): String {
        val envName = path.joinToString("__").replace('-', '_')
        println(envName)
        return System.getenv(envName) ?: rootNode.node(*path).string!!
    }
}