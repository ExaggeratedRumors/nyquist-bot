package com.twitch.nyquistbot.transmission

import com.twitch.nyquistbot.model.Message

class Sender (
    private val connection: Connection,
) {
    fun sendChatMessage(chatMessage: Message) {
        connection.send("PRIVMSG #${chatMessage.channel} :${chatMessage.chatText}")
    }

    fun sendMessage(message: String) {
        connection.send(message)
    }
}
