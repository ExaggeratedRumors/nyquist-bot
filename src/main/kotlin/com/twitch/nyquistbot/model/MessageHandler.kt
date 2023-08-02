package com.twitch.nyquistbot.model

import com.twitch.nyquistbot.commands.Command
import com.twitch.nyquistbot.model.ChatMessage.MessageType
import com.twitch.nyquistbot.transmission.Sender
import com.twitch.nyquistbot.utils.BotProperties
import com.twitch.nyquistbot.utils.Configuration

class MessageHandler (
    private val registeredCommands: Map<String, Command>,
    private val sender: Sender,
    private val properties: BotProperties
){
    fun handleMessage(message: ChatMessage) {
        if(message.type == MessageType.CONNECTION) {
            println("ENGINE: Bot has been authorised")
            return
        }
        if (message.type == MessageType.PING) {
            println("ENGINE: Responded to ping")
            sender.sendServerMessage(message.message.replace("PING", "PONG"))
            return
        }
        if (message.type == MessageType.CHAT) {
            if(message.prefix != properties.configuration.prefix) return
            registeredCommands.forEach { (_, v) ->
                if(v.call == message.command) {
                    println("ENGINE: Respond to command ${v.call}")
                    v.execute(message, properties, sender)
                    return@forEach
                }
            }
            return
        }
    }
}