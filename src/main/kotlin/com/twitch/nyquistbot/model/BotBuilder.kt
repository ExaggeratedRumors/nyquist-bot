package com.twitch.nyquistbot.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.twitch.nyquistbot.commands.Command
import com.twitch.nyquistbot.commands.CommandsContainer
import com.twitch.nyquistbot.utils.*
import com.twitch.nyquistbot.utils.YamlReader.Companion.readYamlObject
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class BotBuilder {
    fun loadBotProperties(): BotProperties {
        val configuration = loadConfiguration()
        val oauthToken = loadOAuthToken(configuration)
        return BotProperties(configuration, oauthToken)
    }
    private fun loadConfiguration(): Configuration = readYamlObject(
        CONFIGURATION_PATH,
        Configuration::class.java
    )

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

    fun loadCommands() = readYamlObject(
        COMMANDS_PATH,
        CommandsList::class.java
    ).installCommands()

    private fun CommandsList.installCommands(): Map<String, Command> {
        val registeredCommands = HashMap<String, Command>()
        CommandsContainer.implementedCommands.forEach {
            if(this.commands[it.javaClass.simpleName]?.enabled == true) {
                registeredCommands[it.javaClass.simpleName] = it
                it.call = this.commands[it.javaClass.simpleName]!!.call
            }
        }
        return registeredCommands
    }
}