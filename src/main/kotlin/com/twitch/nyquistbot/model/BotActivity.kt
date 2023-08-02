package com.twitch.nyquistbot.model

import com.twitch.nyquistbot.commands.Command
import com.twitch.nyquistbot.transmission.Connection
import com.twitch.nyquistbot.transmission.Receiver
import com.twitch.nyquistbot.transmission.Sender
import com.twitch.nyquistbot.utils.BotProperties

class BotActivity {
    companion object {
        lateinit var registeredCommands: Map<String, Command>
        lateinit var connection: Connection
        lateinit var properties: BotProperties
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
            properties = botBuilder.loadBotProperties()
            registeredCommands = botBuilder.loadCommands()
        }

        private fun connectBot() {
            connection = Connection(
                properties.configuration.server.host,
                properties.configuration.server.port
            )
            connection.start()
        }

        private fun registerBot() {
            sender = Sender(connection)
            sender.sendServerMessage("PASS ${properties.configuration.oauth.oauth_password}")
            sender.sendServerMessage("NICK ${properties.configuration.api.twitch_nickname}")
            receiver = Receiver(connection)

            handler = MessageHandler(registeredCommands, sender, properties)
            receiver.configureReceiver { handler.handleMessage(it) }

            properties.configuration.channels.forEach {
                sender.sendServerMessage("JOIN #$it")
            }
        }
    }
}