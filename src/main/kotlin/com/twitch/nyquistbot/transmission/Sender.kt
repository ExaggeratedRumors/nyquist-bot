package com.twitch.nyquistbot.transmission

import com.twitch.nyquistbot.model.BotBuilder

class Sender (
    private val connection: Connection,
    private val configuration: BotBuilder.Configuration
) {

    fun serviceMessage(message: String) {
        if(message.contains("Welcome, GLHF!")) {
            println("AUTHENTICATED")
        } else if (message.startsWith("PING")) {
            println("RESPOND TO PING")
            connection.send(message.replace("PING", "PONG"))
        } else {

        }
    }

    fun sendChatResponse(message: String) {
        connection.send("PRIVMSG #${configuration.channels[0]} :${message}")
    }
}
