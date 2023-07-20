package com.twitch.nyquistbot.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.twitch.nyquistbot.commands.Command
import com.twitch.nyquistbot.commands.CommandsContainer
import com.twitch.nyquistbot.utils.YamlReader.Companion.readYamlObject
import com.twitch.nyquistbot.utils.COMMANDS_PATH
import com.twitch.nyquistbot.utils.CONFIGURATION_PATH

class BotBuilder {
    fun loadConfiguration(): Configuration = readYamlObject(
        CONFIGURATION_PATH,
        Configuration::class.java
    )

    fun loadCommands() = readYamlObject(
        COMMANDS_PATH,
        CommandsList::class.java
    ).installCommands()

    private fun CommandsList.installCommands(): Map<String, Command> {
        val cc = CommandsContainer()
        val registeredCommands = HashMap<String, Command>()
        cc.implementedCommands.forEach {
            if(this.commands[it.javaClass.simpleName]!!) registeredCommands[it.javaClass.simpleName] = it
        }
        return registeredCommands
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class CommandsList (
        val commands: Map<String, Boolean> = mapOf()
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Configuration(
        val server: Server = Server(),
        val api: Api = Api(),
        val channels: List<String> = listOf(),
        val prefix: String
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Server(
        val host: String = String(),
        val port: Int = 0,
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Api(
        val twitchNickname: String = String(),
        val twitchClientId: String = String(),
        val twitchClientSecret: String = String(),
        val oauthPassword: String = String(),
        val accessToken: String = String()
    )
}