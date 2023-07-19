package com.twitch.nyquistbot.model

import com.twitch.nyquistbot.commands.Command
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import reactor.core.scheduler.Schedulers

class Activity {
    companion object {
        lateinit var registeredCommands: Map<String, Command>
        lateinit var connection: Connection
        lateinit var configuration: BotBuilder.Configuration

        fun startActivity() {
            val botBuilder = BotBuilder()
            configuration = botBuilder.loadConfiguration()
            registeredCommands = botBuilder.loadCommands()
            connection = Connection(
                configuration.server.host,
                configuration.server.port
            )
            connection.start()

            val meterRegistry = SimpleMeterRegistry()
            Schedulers.enableMetrics()
        }

        fun authorizeActivity() {
            connection.send("PASS ${configuration.api.irc}")
            connection.send("NICK ${configuration.api.twitchClientId}")

            connection
                .queue
                .metrics()
                .subscribeOn(
                    Schedulers.parallel()
                )
                .subscribe {
                    if(!it.startsWith(configuration.prefix)) return@subscribe
                    registeredCommands[it.substring(1)]?.execute(connection)
                }
        }
    }
}