package com.twitch.nyquistbot.model

import io.micrometer.core.instrument.simple.SimpleMeterRegistry

class Activity {
    companion object {
        lateinit var commands: BotBuilder.CommandsList

        fun startActivity() {
            val botBuilder = BotBuilder()
            val configuration = botBuilder.loadConfiguration()
            commands = botBuilder.loadCommands()
            val connection = Connection(
                configuration.server.host,
                configuration.server.port
            )
            connection.start()

            val meterRegistry = SimpleMeterRegistry()
        }
    }
}