package com.twitch.nyquistbot.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.twitch.nyquistbot.commands.Command
import com.twitch.nyquistbot.commands.CommandsContainer
import com.twitch.nyquistbot.dto.AppTokenResponse
import com.twitch.nyquistbot.dto.CommandsList
import com.twitch.nyquistbot.dto.UserTokenResponse
import com.twitch.nyquistbot.transmission.BotRequest
import com.twitch.nyquistbot.transmission.Connection
import com.twitch.nyquistbot.transmission.Receiver
import com.twitch.nyquistbot.transmission.Sender
import com.twitch.nyquistbot.utils.ResourcesContainer
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Request

class BotBuilder {
    fun loadResources() {
        ResourcesContainer.loadResources()
    }

    fun loadBotProperties(): BotProperties {
        return BotProperties(
            getAppAccessToken(),
            getUserAccessToken()
        )
    }

    private fun getAppAccessToken(): String? {
        val requestBody = FormBody.Builder()
            .add("client_id", ResourcesContainer.configuration.api.twitch_client_id)
            .add("client_secret", ResourcesContainer.configuration.api.twitch_client_secret)
            .add("grant_type", "client_credentials")
            .build()

        val request = Request.Builder()
            .url(ResourcesContainer.configuration.oauth.token_url)
            .post(requestBody)
            .build()

        BotRequest.executeRequest(request) { it ->
            if (!it.isSuccessful) {
                println("ENGINE: App token cannot be gained")
                return null
            }

            val response = ObjectMapper().readValue(
                it.body?.string(),
                AppTokenResponse::class.java
            )
            return response.access_token
        }
    }

    private fun generateAccessCodeUrl(): String {
        val urlBuilder = ResourcesContainer.configuration.oauth.authorization_url
            .toHttpUrlOrNull()
            ?.newBuilder()
            ?.addQueryParameter("client_id", ResourcesContainer.configuration.api.twitch_client_id)
            ?.addQueryParameter("redirect_uri", "http://localhost:3000")
            ?.addQueryParameter("response_type", "code")
            ?.addQueryParameter("scope", "moderator:read:chatters moderation:read channel:manage:moderators")

        val authorizationUrl = urlBuilder?.build().toString()
        println("ENGINE: Access code has been generated: $authorizationUrl")
        return authorizationUrl
    }

    private fun getUserAccessToken(): String? {
        val requestBody = FormBody.Builder()
            .add("client_id", ResourcesContainer.configuration.api.twitch_client_id)
            .add("client_secret", ResourcesContainer.configuration.api.twitch_client_secret)
            .add("code", ResourcesContainer.configuration.oauth.code)
            .add("grant_type", "authorization_code")
            .add("redirect_uri", "http://localhost:3000")
            .build()

        val request = Request.Builder()
            .url(ResourcesContainer.configuration.oauth.token_url)
            .post(requestBody)
            .build()

        BotRequest.executeRequest(request) { response ->
            if (!response.isSuccessful) {
                println("ENGINE: User token cannot be gained")
                return null
            }

            val userTokenResponse = ObjectMapper().readValue(
                response.body?.string(),
                UserTokenResponse::class.java
            )
            return userTokenResponse.access_token
        }
    }

    fun registerCommands(commandsList: CommandsList): Map<String, Command> {
        val registeredCommands = HashMap<String, Command>()
        CommandsContainer.implementedCommands.forEach {
            if(commandsList.commands[it.javaClass.simpleName]?.enabled == true) {
                registeredCommands[it.javaClass.simpleName] = it
                it.call = commandsList.commands[it.javaClass.simpleName]!!.call
            }
        }
        return registeredCommands
    }

    fun startConnection(): Connection {
        val connection = Connection(
            ResourcesContainer.configuration.server.host,
            ResourcesContainer.configuration.server.port
        )
        connection.start()
        return connection
    }

    fun registerSender(connection: Connection): Sender {
        val sender = Sender(connection)
        sender.sendServerMessage("PASS ${ResourcesContainer.configuration.oauth.oauth_password}")
        sender.sendServerMessage("NICK ${ResourcesContainer.configuration.api.twitch_nickname}")
        return sender
    }

    fun registerReceiver(connection: Connection, handler: MessageHandler): Receiver {
        val receiver = Receiver(connection)
        receiver.configureReceiver { handler.handleMessage(it) }
        return receiver
    }

    fun joinChannels(sender: Sender) {
        ResourcesContainer.configuration.channels.forEach {
            sender.sendServerMessage("JOIN #$it")
        }
    }
}