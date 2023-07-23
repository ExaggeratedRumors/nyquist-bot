package com.twitch.nyquistbot.model

import com.twitch.nyquistbot.commands.Command
import com.twitch.nyquistbot.commands.CommandsContainer
import com.twitch.nyquistbot.utils.COMMANDS_PATH
import com.twitch.nyquistbot.utils.CONFIGURATION_PATH
import com.twitch.nyquistbot.utils.CommandsList
import com.twitch.nyquistbot.utils.Configuration
import com.twitch.nyquistbot.utils.YamlReader.Companion.readYamlObject

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
        val registeredCommands = HashMap<String, Command>()
        CommandsContainer.implementedCommands.forEach {
            if(this.commands[it.javaClass.simpleName]?.enabled == true) {
                registeredCommands[it.javaClass.simpleName] = it
                it.call = this.commands[it.javaClass.simpleName]!!.call
            }
        }
        return registeredCommands
    }
}