package com.twitch.nyquistbot.utils

import com.twitch.nyquistbot.dto.CommandsList
import com.twitch.nyquistbot.dto.Configuration
import com.twitch.nyquistbot.dto.IllegalWordsList
import com.twitch.nyquistbot.dto.PhrasesList

class ResourcesContainer {
    companion object {
        var configuration: Configuration = Configuration()
        var commandsList: CommandsList = CommandsList()
        var illegalWordsList: IllegalWordsList = IllegalWordsList()
        var phrasesList: PhrasesList = PhrasesList()

        init {
            loadResources()
        }

        fun loadResources() {
            configuration = YamlReader.readYamlObject(
                CONFIGURATION_PATH,
                Configuration::class.java
            )

            commandsList = YamlReader.readYamlObject(
                COMMANDS_PATH,
                CommandsList::class.java
            )

            illegalWordsList = YamlReader.readYamlObject(
                ILLEGAL_WORDS_PATH,
                IllegalWordsList::class.java
            )

            phrasesList = YamlReader.readYamlObject(
                PHRASES_PATH,
                PhrasesList::class.java
            )
        }
    }
}