package com.twitch.nyquistbot.transmission

import com.twitch.nyquistbot.model.ChatMessage

class Sender (
    private val connection: Connection,
) {
    fun sendChatMessage(chatMessage: ChatMessage) {
        connection.send("PRIVMSG #${chatMessage.channel} :${chatMessage.text}")
    }

    fun sendMessage(message: String) {
        connection.send(message)
    }
}
