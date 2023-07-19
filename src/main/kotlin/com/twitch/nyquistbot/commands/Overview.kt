package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.Connection

class Overview: Command {
    var counter: Int = 0
    override fun execute(connection: Connection) {
        counter += 1
        connection.send("Test")
    }

    override fun getCall() = "overview"
    override fun getCounter() = counter
}