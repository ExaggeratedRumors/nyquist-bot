package com.twitch.nyquistbot.model

import com.twitch.nyquistbot.commands.Command
import com.twitch.nyquistbot.model.Message.MessageType
import com.twitch.nyquistbot.transmission.Sender
import com.twitch.nyquistbot.utils.Configuration

class MessageHandler (
    private val registeredCommands: Map<String, Command>,
    private val sender: Sender,
    private val configuration: Configuration
){
    fun handleMessage(message: Message) {
        if(message.type == MessageType.CONNECTION) {
            println("ENGINE: Bot has been authorised")
            return
        }
        if (message.type == MessageType.PING) {
            println("ENGINE: Responded to ping")
            sender.sendMessage(message.message.replace("PING", "PONG"))
            return
        }
        if (message.type == MessageType.CHAT) {
            if(message.prefix != configuration.prefix) return
            registeredCommands.forEach { (_, v) ->
                if(v.getCall() == message.command) {
                    println("ENGINE: Respond to command ${v.getCall()}")
                    v.execute(message, sender)
                    return@forEach
                }
            }
            return
        }
    }
}