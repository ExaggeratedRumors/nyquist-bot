package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.transmission.Connection

interface Command {
    fun execute(connection: Connection)
    fun getCall(): String
    fun getCounter(): Int
}