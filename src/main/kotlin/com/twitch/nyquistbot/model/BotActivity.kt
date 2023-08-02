package com.twitch.nyquistbot.model

import com.twitch.nyquistbot.commands.Command
import com.twitch.nyquistbot.transmission.Connection
import com.twitch.nyquistbot.transmission.Receiver
import com.twitch.nyquistbot.transmission.Sender
import com.twitch.nyquistbot.utils.ResourcesContainer

class BotActivity {
    private lateinit var registeredCommands: Map<String, Command>
    private lateinit var connection: Connection
    private lateinit var properties: BotProperties
    private lateinit var handler: MessageHandler
    private lateinit var receiver: Receiver
    private lateinit var sender: Sender

    fun startActivity() {
        loadResources()
        buildBot()
        connectBot()
        registerBot()
    }

    private fun loadResources() {
        ResourcesContainer.loadResources()
    }

    private fun buildBot() {
        val botBuilder = BotBuilder()
        properties = botBuilder.loadBotProperties()
        registeredCommands = botBuilder.registerCommands(ResourcesContainer.commandsList)
    }

    private fun connectBot() {
        connection = Connection(
            ResourcesContainer.configuration.server.host,
            ResourcesContainer.configuration.server.port
        )
        connection.start()
    }

    private fun registerBot() {
        sender = Sender(connection)
        sender.sendServerMessage("PASS ${ResourcesContainer.configuration.oauth.oauth_password}")
        sender.sendServerMessage("NICK ${ResourcesContainer.configuration.api.twitch_nickname}")
        receiver = Receiver(connection)

        handler = MessageHandler(registeredCommands, sender, properties)
        receiver.configureReceiver { handler.handleMessage(it) }

        ResourcesContainer.configuration.channels.forEach {
            sender.sendServerMessage("JOIN #$it")
        }
    }
}