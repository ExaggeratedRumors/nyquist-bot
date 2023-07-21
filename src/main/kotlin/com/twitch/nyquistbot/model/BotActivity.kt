package com.twitch.nyquistbot.model

import com.twitch.nyquistbot.commands.Command
import com.twitch.nyquistbot.transmission.Connection
import com.twitch.nyquistbot.transmission.Receiver
import com.twitch.nyquistbot.transmission.Sender
import com.twitch.nyquistbot.utils.Configuration

class BotActivity {
    companion object {
        lateinit var registeredCommands: Map<String, Command>
        lateinit var connection: Connection
        lateinit var configuration: Configuration
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
        }

        private fun connectBot() {
            connection = Connection(
                configuration.server.host,
                configuration.server.port
            )
            connection.start()
        }

        private fun registerBot() {
            sender = Sender(connection)
            sender.sendMessage("PASS ${configuration.api.oauth_password}")
            sender.sendMessage("NICK ${configuration.api.twitch_nickname}")
            receiver = Receiver(connection)

            handler = MessageHandler(registeredCommands, sender, configuration)
            receiver.configureReceiver { handler.handleMessage(it) }

            sender.sendMessage("JOIN #${configuration.channels[0]}")
        }
    }
}