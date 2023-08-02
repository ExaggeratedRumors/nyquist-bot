package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.ChatMessage
import com.twitch.nyquistbot.transmission.Sender
import com.twitch.nyquistbot.utils.BotProperties
import com.twitch.nyquistbot.utils.PHRASES_PATH
import com.twitch.nyquistbot.utils.PhrasesList
import com.twitch.nyquistbot.utils.YamlReader.Companion.readYamlObject

class RandomPhrase : Command() {
    companion object {
        val phrases = readYamlObject(
            PHRASES_PATH,
            PhrasesList::class.java
        ).phrases
    }

    override fun execute(chatMessage: ChatMessage, properties: BotProperties, sender: Sender) {
        sender.responseToMessage(chatMessage, phrases.random())
    }
}