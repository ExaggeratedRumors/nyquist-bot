package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.BotProperties
import com.twitch.nyquistbot.model.ChatMessage
import com.twitch.nyquistbot.transmission.Sender
import com.twitch.nyquistbot.utils.ResourcesContainer

class Echo: Command() {
    override fun execute(chatMessage: ChatMessage, properties: BotProperties, sender: Sender) {
        ResourcesContainer.illegalWordsList.illegal_words.forEach {
            if (chatMessage.chatText.contains(it)) {
                println("ENGINE: Refuse to $call illegal word")
                sender.responseToMessage(chatMessage, "@${chatMessage.author} Nice try")
                return
            }
        }
        sender.responseToMessage(chatMessage, chatMessage.chatText)
    }
}
