package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.Message
import com.twitch.nyquistbot.transmission.Sender

abstract class Command {
    var call = String()
    abstract fun execute(chatMessage: Message, sender: Sender)
}