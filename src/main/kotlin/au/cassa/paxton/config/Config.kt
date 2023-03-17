package au.cassa.paxton.config

import org.spongepowered.configurate.CommentedConfigurationNode

abstract class Config(
    id: String
) {

    lateinit var rootNode: CommentedConfigurationNode
        protected set

    abstract fun load()

    abstract fun save()

}