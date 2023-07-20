package com.twitch.nyquistbot.model

import com.twitch.nyquistbot.commands.Command
import com.twitch.nyquistbot.transmission.Sender

class MessageHandler (
    private val registeredCommands: Map<String, Command>,
    private val sender: Sender
){
    fun handleMessage(message: String) {
        if(message.contains("Welcome, GLHF!")) {
            println("AUTHENTICATED")
        } else if (message.startsWith("PING")) {
            println("RESPOND TO PING")
            sender.sendMessage(message.replace("PING", "PONG"))
        } else {
            val chatMessage = ChatMessage(message)
            registeredCommands.forEach { (_, v) ->
                if(v.getCall() == chatMessage.text)
                    v.execute(chatMessage, sender)
            }
        }
    }
}