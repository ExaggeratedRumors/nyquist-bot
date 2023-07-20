package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.transmission.Connection

class Echo: Command {
    var counter: Int = 0
    override fun execute(connection: Connection) {
        counter += 1
        connection.send("Test")
    }

    override fun getCall() = "echo"
    override fun getCounter() = counter
}