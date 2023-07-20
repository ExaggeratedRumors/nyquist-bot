package com.twitch.nyquistbot.transmission

class ChatMessage (private val message: String) {
    lateinit var text: String
    lateinit var author: String
    lateinit var channel: String
}