package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.BotProperties
import com.twitch.nyquistbot.model.ChatMessage
import com.twitch.nyquistbot.transmission.Sender

abstract class Command {
    var call = String()
    abstract fun execute(chatMessage: ChatMessage, properties: BotProperties, sender: Sender)
}