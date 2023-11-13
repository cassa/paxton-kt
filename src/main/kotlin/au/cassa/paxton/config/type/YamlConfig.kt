@file:Suppress("unused")

package au.cassa.paxton.config.type

import au.cassa.paxton.config.Config
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import kotlin.io.path.Path

abstract class YamlConfig(
    id: String
) : Config(
    id
) {

    val relativePath = Path("${id}.yml")

    private val loader = YamlConfigurationLoader
        .builder()
        .path(relativePath)
        .build()

    override fun load() {
        saveIfNotExists()
        rootNode = loader.load()
    }

    override fun save() {
        loader.save(rootNode)
    }

    override fun saveIfNotExists() {
        TODO("Not yet implemented")
    }

}