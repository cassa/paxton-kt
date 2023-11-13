package au.cassa.paxton.config.type

import au.cassa.paxton.Paxton
import au.cassa.paxton.config.Config
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.io.File
import java.io.FileOutputStream
import kotlin.io.path.Path
import kotlin.io.path.pathString

abstract class JsonConfig(
    id: String
) : Config(
    id
) {

    val relativePath = Path("${id}.json")

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
        // file object referencing the relative path
        val file = File(relativePath.pathString)

        // try to create the file - if it already exists, return
        file.parentFile.mkdirs()
        val alreadyExists = !file.createNewFile()
        if(alreadyExists) return

        // create input stream referencing resource file
        val istream = Paxton.javaClass.classLoader.getResourceAsStream(relativePath.pathString)!!

        // create output stream referencing destination file
        val ostream = FileOutputStream(file)

        // write bytes from istream to ostream
        ostream.write(istream.readBytes())
    }

}