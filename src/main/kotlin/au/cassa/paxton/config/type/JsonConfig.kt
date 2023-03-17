package au.cassa.paxton.config.type

import au.cassa.paxton.config.Config
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import kotlin.io.path.Path

abstract class JsonConfig(
    id: String
) : Config(
    id
) {

    private val path = Path("${id}.json")

    private val loader = YamlConfigurationLoader
        .builder()
        .path(path)
        .build()

    override fun load() {
        rootNode = loader.load()
    }

    override fun save() {
        loader.save(rootNode)
    }

}