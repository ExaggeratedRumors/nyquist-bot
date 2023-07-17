package com.twitch.nyquistbot.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.twitch.nyquistbot.utils.commandsPath
import com.twitch.nyquistbot.utils.configurationPath
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

class BotBuilder {
    fun loadConfiguration(): Configuration = try {
        Files.newBufferedReader(Paths.get(configurationPath))
            .use {
                getMapper().readValue(it, Configuration::class.java)
            }
    } catch (e: Exception) {
        println("Cannot read configuration YAML file.")
        e.printStackTrace()
        exitProcess(1)
    }

    fun loadCommands(): CommandsList = try {
        Files.newBufferedReader(Paths.get(commandsPath))
            .use {
                getMapper().readValue(it, CommandsList::class.java)
            }
    } catch (e: Exception) {
        println("Cannot read commands YAML file.")
        e.printStackTrace()
        exitProcess(1)
    }

    private fun getMapper() = ObjectMapper(YAMLFactory()).apply {
        this.registerModule(
            KotlinModule.Builder()
                .withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, false)
                .configure(KotlinFeature.NullToEmptyMap, false)
                .configure(KotlinFeature.NullIsSameAsDefault, false)
                .configure(KotlinFeature.SingletonSupport, false)
                .configure(KotlinFeature.StrictNullChecks, false)
                .build()
        )
    }
}