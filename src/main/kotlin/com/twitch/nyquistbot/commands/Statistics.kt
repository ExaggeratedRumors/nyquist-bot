package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.BotProperties
import com.twitch.nyquistbot.model.ChatMessage
import com.twitch.nyquistbot.transmission.Sender

class Statistics: Command() {
    override fun execute(chatMessage: ChatMessage, properties: BotProperties, sender: Sender) {
        sender.responseToMessage(chatMessage, chatMessage.author)
    }
}