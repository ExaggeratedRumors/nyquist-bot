package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.Connection

class Overview: Command {
    override fun execute(connection: Connection) {
        connection.send("Test")
    }
}