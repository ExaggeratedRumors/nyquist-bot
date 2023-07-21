package com.twitch.nyquistbot.model

class Message (val message: String) {
    enum class MessageType {
        CONNECTION, PING, CHAT, UNSERVICED
    }

    var type: MessageType = MessageType.UNSERVICED
    var chatText: String = String()
    var author: String = String()
    var channel: String = String()
    var command: String = String()
    var prefix: String = String()

    init {
        if (message.contains("Welcome, GLHF!"))
            type = MessageType.CONNECTION
        else if (message.contains("PING"))
            type = MessageType.PING
        else if (message.contains("PRIVMSG")) {
            type = MessageType.CHAT
            decomposeChatMessage()
        }
    }

    private fun decomposeChatMessage() {
        val splitMessage = message.split(":")
        val splitMeta = splitMessage[1].split(" ")
        author = splitMeta[0].split("!")[0]
        channel = splitMeta[2].replace("#", "").trim()
        val metadata = ":${splitMeta[0]} PRIVMSG #$channel :"
        chatText = message.replace(metadata, "")
        prefix = chatText[0].toString()
        command = chatText.split(" ")[0].substring(1)
        chatText = chatText.substring("$prefix$command".length)
    }

    fun clone(): Message {
        return Message(message)
    }
}