package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.ChatMessage
import com.twitch.nyquistbot.transmission.Sender
import com.twitch.nyquistbot.utils.BotProperties

abstract class Command {
    var call = String()
    abstract fun execute(chatMessage: ChatMessage, properties: BotProperties, sender: Sender)
}