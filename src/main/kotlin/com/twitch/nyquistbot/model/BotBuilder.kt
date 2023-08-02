package com.twitch.nyquistbot.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.twitch.nyquistbot.commands.Command
import com.twitch.nyquistbot.commands.CommandsContainer
import com.twitch.nyquistbot.dto.CommandsList
import com.twitch.nyquistbot.dto.Configuration
import com.twitch.nyquistbot.dto.OAuthTokenResponse
import com.twitch.nyquistbot.utils.ResourcesContainer
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class BotBuilder {
    fun loadBotProperties(): BotProperties {
        val oauthToken = loadOAuthToken(ResourcesContainer.configuration)
        return BotProperties(oauthToken)
    }

    private fun loadOAuthToken(configuration: Configuration): String? {
        val requestBody = FormBody.Builder()
            .add("client_id", configuration.api.twitch_client_id)
            .add("client_secret", configuration.api.twitch_client_secret)
            .add("grant_type", "client_credentials")
            .build()

        val request = Request.Builder()
            .url(configuration.oauth.url)
            .post(requestBody)
            .build()

        OkHttpClient().newCall(request).execute().use { it ->
            if (!it.isSuccessful) {
                println("ENGINE: Bot cannot be authorized")
                return null
            }

            val response = ObjectMapper().readValue(
                it.body?.string(),
                OAuthTokenResponse::class.java
            )
            return response.access_token
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
}