package com.twitch.nyquistbot.commands

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.twitch.nyquistbot.transmission.Connection
import com.twitch.nyquistbot.utils.YamlReader.Companion.readYamlObject
import com.twitch.nyquistbot.utils.PHRASES_PATH

class RandomPhrase : Command {
    companion object {
        var counter: Int = 0
        val phrases = readYamlObject(
            PHRASES_PATH,
            PhrasesList::class.java
        ).phrases
    }

    override fun execute(connection: Connection) {
        counter += 1
        connection.send(phrases.random())
    }

    override fun getCall() = "phrase"
    override fun getCounter() = counter

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class PhrasesList (
        val phrases: List<String> = listOf()
    )
}