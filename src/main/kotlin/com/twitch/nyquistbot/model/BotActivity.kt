package com.twitch.nyquistbot.model

import com.twitch.nyquistbot.commands.Command
import com.twitch.nyquistbot.transmission.Connection
import com.twitch.nyquistbot.transmission.Receiver
import com.twitch.nyquistbot.transmission.Sender
import com.twitch.nyquistbot.utils.ResourcesContainer

class BotActivity {
    private val botBuilder = BotBuilder()
    private lateinit var registeredCommands: Map<String, Command>
    private lateinit var connection: Connection
    private lateinit var properties: BotProperties
    private lateinit var handler: MessageHandler
    private lateinit var receiver: Receiver
    private lateinit var sender: Sender

    fun startActivity() {
        loadResources()
        loadProperties()
        registerCommands()
        connectBot()
        registerTransmission()
        joinChannels()
    }

    private fun loadResources() {
        botBuilder.loadResources()
    }

    private fun loadProperties() {
        properties = botBuilder.loadBotProperties()
    }

    private fun registerCommands() {
        registeredCommands = botBuilder.registerCommands(ResourcesContainer.commandsList)
    }

    private fun connectBot() {
        connection = botBuilder.startConnection()
    }

    private fun registerTransmission() {
        sender = botBuilder.registerSender(connection)
        handler = MessageHandler(registeredCommands, sender, properties)
        receiver = botBuilder.registerReceiver(connection, handler)
    }

    private fun joinChannels() {
        botBuilder.joinChannels(sender)
    }
}