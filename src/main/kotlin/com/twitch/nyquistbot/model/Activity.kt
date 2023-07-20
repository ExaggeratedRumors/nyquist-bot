package com.twitch.nyquistbot.model

import com.twitch.nyquistbot.commands.Command
import com.twitch.nyquistbot.transmission.Connection
import com.twitch.nyquistbot.utils.TIMEOUT
import reactor.core.scheduler.Schedulers

class Activity {
    companion object {
        lateinit var registeredCommands: Map<String, Command>
        lateinit var connection: Connection
        lateinit var configuration: BotBuilder.Configuration

        fun startActivity() {
            buildBot()
            connectBot()
            authorizeBot()
        }

        private fun buildBot() {
            val botBuilder = BotBuilder()
            configuration = botBuilder.loadConfiguration()
            registeredCommands = botBuilder.loadCommands()


            //val meterRegistry = SimpleMeterRegistry()
            //Schedulers.enableMetrics()
        }

        private fun connectBot() {
            connection = Connection(
                configuration.server.host,
                configuration.server.port
            )
            connection.start()
        }

        private fun authorizeBot() {
            connection.send("PASS ${configuration.api.oauthPassword}")
            connection.send("NICK ${configuration.api.twitchNickname}")
            connection
                .queueFlux
                .metrics()
                .subscribeOn(
                    Schedulers.parallel()
                )
                .subscribe {
                    if(!it.startsWith(configuration.prefix)) return@subscribe
                    if(!connection.isConnected()) return@subscribe
                    registeredCommands[it.substring(1)]?.execute(connection)
                }

            Thread.sleep(TIMEOUT * 1000L)
            connection.send("JOIN #${configuration.channels[0]}")

        }
    }
}