package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.Message
import com.twitch.nyquistbot.transmission.Sender

interface Command {
    fun execute(chatMessage: Message, sender: Sender)
    fun getCall(): String
}