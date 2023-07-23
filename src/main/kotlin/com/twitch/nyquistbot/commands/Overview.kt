package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.Message
import com.twitch.nyquistbot.transmission.Sender

class Overview: Command() {
    override fun execute(chatMessage: Message, sender: Sender) {
        val newMessage = chatMessage.clone()
        newMessage.author = "bot"
        newMessage.chatText = "Overview test"
        sender.sendChatMessage(newMessage)
    }
}