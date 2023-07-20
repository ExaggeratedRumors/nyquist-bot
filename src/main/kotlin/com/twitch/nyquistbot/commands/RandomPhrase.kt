package com.twitch.nyquistbot.commands

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.twitch.nyquistbot.model.ChatMessage
import com.twitch.nyquistbot.transmission.Sender
import com.twitch.nyquistbot.utils.PHRASES_PATH
import com.twitch.nyquistbot.utils.YamlReader.Companion.readYamlObject

class RandomPhrase : Command {
    companion object {
        val phrases = readYamlObject(
            PHRASES_PATH,
            PhrasesList::class.java
        ).phrases
    }

    override fun execute(chatMessage: ChatMessage, sender: Sender) {
        sender.sendMessage(phrases.random())
    }

    override fun getCall() = "phrase"

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class PhrasesList (
        val phrases: List<String> = listOf()
    )
}