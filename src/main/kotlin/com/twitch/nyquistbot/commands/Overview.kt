package com.twitch.nyquistbot.commands

import com.fasterxml.jackson.databind.ObjectMapper
import com.twitch.nyquistbot.model.ChatMessage
import com.twitch.nyquistbot.transmission.Sender
import com.twitch.nyquistbot.utils.BotProperties
import com.twitch.nyquistbot.utils.UsersResponse
import okhttp3.OkHttpClient
import okhttp3.Request

class Overview: Command() {
    private val objectMapper = ObjectMapper()
    private val client = OkHttpClient()

    override fun execute(chatMessage: ChatMessage, properties: BotProperties, sender: Sender) {
        if(properties.oauthToken == null) {
            println("ENGINE: Cannot execute Overview command, because OAuth token is null")
            return
        }
        val request = Request.Builder()
            .url("${properties.configuration.api.url}users?login=${chatMessage.chatText}")
            .addHeader("Client-ID", properties.configuration.api.twitch_client_id)
            .addHeader("Authorization", "Bearer ${properties.oauthToken}")
            .build()

        client.newCall(request)
            .execute()
            .use {
                if(!it.isSuccessful) {
                    sender.responseToMessage(chatMessage , "Account doesn't exist.")
                    return
                }

                val usersResponse = objectMapper
                    .readValue(it.body?.string(), UsersResponse::class.java)
                    .data?.getOrNull(0)

                if(usersResponse?.created_at != null)
                    sender.responseToMessage(chatMessage, usersResponse.created_at)
            }
    }
}