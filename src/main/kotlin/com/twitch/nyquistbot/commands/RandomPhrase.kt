package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.Message
import com.twitch.nyquistbot.transmission.Sender
import com.twitch.nyquistbot.utils.PHRASES_PATH
import com.twitch.nyquistbot.utils.PhrasesList
import com.twitch.nyquistbot.utils.YamlReader.Companion.readYamlObject

class RandomPhrase : Command {
    companion object {
        val phrases = readYamlObject(
            PHRASES_PATH,
            PhrasesList::class.java
        ).phrases
    }

    override fun execute(chatMessage: Message, sender: Sender) {
        val newMessage = chatMessage.clone()
        newMessage.author = "bot"
        newMessage.chatText = "hrases.random()"
        sender.sendChatMessage(newMessage)
    }

    override fun getCall() = "phrase"
}