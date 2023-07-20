package com.twitch.nyquistbot.model

import com.twitch.nyquistbot.commands.Command
import com.twitch.nyquistbot.transmission.Connection
import com.twitch.nyquistbot.transmission.Receiver
import com.twitch.nyquistbot.transmission.Sender

class Activity {
    companion object {
        lateinit var registeredCommands: Map<String, Command>
        lateinit var connection: Connection
        lateinit var configuration: BotBuilder.Configuration
        lateinit var handler: MessageHandler
        lateinit var receiver: Receiver
        lateinit var sender: Sender

        fun startActivity() {
            buildBot()
            connectBot()
            registerBot()
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

        private fun registerBot() {
            println("sender")
            sender = Sender(connection)

            sender.sendMessage("PASS ${configuration.api.oauthPassword}")
            sender.sendMessage("NICK ${configuration.api.twitchNickname}")
            println("receiver")

            receiver = Receiver(connection)
            println("handler")

            handler = MessageHandler(registeredCommands, sender)
            receiver.configureReceiver { println(it)
                handler.handleMessage(it) }
            println("configure")

            (0..5).forEach {
                println("czekamy na polaczenie $it")
                Thread.sleep(1000L)
            }
            //Thread.sleep(TIMEOUT * 1000L)
            sender.sendMessage("JOIN #${configuration.channels[0]}")
        }
    }
}