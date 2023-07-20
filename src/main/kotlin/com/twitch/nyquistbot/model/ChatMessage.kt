package com.twitch.nyquistbot.model

class ChatMessage (private val message: String) {
    var text: String
    var author: String
    var channel: String
    lateinit var command: String
    var isPrefix: Boolean = false

    init {
        val splitMessage = message.split(":")
        val splitMeta = splitMessage[1].split(" ")
        author = splitMeta[0].split("!")[0]
        channel = splitMeta[2].replace("#", "").trim()
        val metadata = ":${splitMeta[0]} PRIVMSG #$channel :"
        text = message.replace(metadata, "")
    }
}