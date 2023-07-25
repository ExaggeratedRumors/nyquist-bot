package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.Message
import com.twitch.nyquistbot.transmission.Sender

class Statistics: Command() {
    override fun execute(chatMessage: Message, sender: Sender) {
        val newMessage = chatMessage.clone()
        newMessage.author = "bot"
        newMessage.chatText = chatMessage.author
        sender.sendChatMessage(newMessage)
    }
}