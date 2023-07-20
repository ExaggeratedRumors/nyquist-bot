package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.ChatMessage
import com.twitch.nyquistbot.transmission.Sender

class Echo: Command {
    override fun execute(chatMessage: ChatMessage, sender: Sender) {
        sender.sendMessage(chatMessage.text)
    }

    override fun getCall() = "echo"
}