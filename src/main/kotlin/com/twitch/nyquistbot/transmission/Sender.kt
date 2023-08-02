package com.twitch.nyquistbot.transmission

import com.twitch.nyquistbot.model.ChatMessage

class Sender (
    private val connection: Connection,
) {
    fun sendChatMessage(chatText: String, channel: String) {
        connection.send("PRIVMSG #$channel :$chatText")
    }

    fun sendServerMessage(message: String) {
        connection.send(message)
    }

    fun responseToMessage(messageToResponse: ChatMessage, responseText: String) {
        sendChatMessage(responseText, messageToResponse.channel)
    }
}
