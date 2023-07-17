package com.twitch.nyquistbot.commands

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.twitch.nyquistbot.model.Connection
import com.twitch.nyquistbot.utils.YamlReader.Companion.readYamlObject
import com.twitch.nyquistbot.utils.phrasesPath

class RandomPhrase : Command {
    companion object {
        val phrases = readYamlObject(
            phrasesPath,
            PhrasesList::class.java
        ).phrases
    }

    override fun execute(connection: Connection) {
        connection.send(phrases.random())
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class PhrasesList (
        val phrases: List<String> = listOf()
    )
}