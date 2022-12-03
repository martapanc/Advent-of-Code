package util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

data class Config(val aocApiDataUrl: String) {}

fun getConfigMapper(): Config {
    val objectMapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
    return objectMapper.readValue(File("src/main/resources/app.yml"), Config::class.java)
}
