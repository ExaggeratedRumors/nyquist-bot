package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.ChatMessage
import com.twitch.nyquistbot.transmission.Sender

class Overview: Command {
    override fun execute(chatMessage: ChatMessage, sender: Sender) {
        sender.sendMessage("Overview test")
    }

    override fun getCall() = "overview"
}