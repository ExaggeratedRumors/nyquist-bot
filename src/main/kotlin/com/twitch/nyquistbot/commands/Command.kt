package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.ChatMessage
import com.twitch.nyquistbot.transmission.Sender

interface Command {
    fun execute(chatMessage: ChatMessage, sender: Sender)
    fun getCall(): String
}