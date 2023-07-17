package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.Connection

interface Command {
    fun execute(connection: Connection)
}