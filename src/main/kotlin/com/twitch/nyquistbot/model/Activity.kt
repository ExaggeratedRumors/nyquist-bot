package com.twitch.nyquistbot.model

import io.micrometer.core.instrument.simple.SimpleMeterRegistry

class Activity {
    companion object {
        fun startActivity() {
            val botBuilder = BotBuilder()
            val configuration = botBuilder.loadConfiguration()
            val connection = Connection(
                configuration.server.host,
                configuration.server.port
            )
            connection.start()

            val meterRegistry = SimpleMeterRegistry()
        }
    }
}