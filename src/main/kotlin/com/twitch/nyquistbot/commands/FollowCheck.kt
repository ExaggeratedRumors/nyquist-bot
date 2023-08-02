package com.twitch.nyquistbot.commands

import com.fasterxml.jackson.databind.ObjectMapper
import com.twitch.nyquistbot.dto.FollowResponse
import com.twitch.nyquistbot.dto.UsersResponse
import com.twitch.nyquistbot.model.BotProperties
import com.twitch.nyquistbot.model.ChatMessage
import com.twitch.nyquistbot.transmission.RequestBuilder
import com.twitch.nyquistbot.transmission.Sender
import okhttp3.OkHttpClient
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class FollowCheck: Command() {
    private val client = OkHttpClient()
    private val objectMapper = ObjectMapper()

    override fun execute(chatMessage: ChatMessage, properties: BotProperties, sender: Sender) {
        if(properties.oauthToken == null) {
            println("ENGINE: Cannot execute $call, because OAuth token is null")
            return
        }
        val splitChatText = chatMessage.chatText.split(" ")

        try {
            val idFrom = getUserID(splitChatText[0], properties)
            val idTo = getUserID(splitChatText[1], properties)
            val request = RequestBuilder.buildRequest(
                RequestBuilder.getFollowRequestUrl(idFrom, idTo),
                properties
            )

            client.newCall(request)
                .execute()
                .use { response ->
                    if (!response.isSuccessful) {
                        println("ENGINE: Refuse to $call incorrect response")
                        throw UserNoExistsException()
                    }

                    val followData = objectMapper
                        .readValue(response.body?.string(), FollowResponse::class.java)
                    if(followData.total == 1) {
                        val period = followData.data?.get(0)?.followed_at.let {
                            val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            return@let Period.between(
                                LocalDate.parse(it?.split("T")?.get(0), dateFormat),
                                LocalDate.now()
                            )
                        }
                        sender.responseToMessage(chatMessage, "${splitChatText[0]} following" +
                                " ${splitChatText[1]} for ${period.years} years")
                    }
                    else sender.responseToMessage(chatMessage , "${splitChatText[0]} isn't following ${splitChatText[1]}")
                }
        } catch (e: UserNoExistsException) {
            sender.responseToMessage(chatMessage , "@${chatMessage.author} account doesn't exist.")
            return
        }
    }

    private fun getUserID(nickname: String, properties: BotProperties): Long {
        if(nickname.isEmpty() || properties.oauthToken == null)
            throw UserNoExistsException()

        val request = RequestBuilder.buildRequest(
            RequestBuilder.getUserRequestUrl(nickname),
            properties
        )

        client.newCall(request)
            .execute()
            .use { response ->
                if(!response.isSuccessful) {
                    println("ENGINE: Refuse to $call non-existing user")
                    throw UserNoExistsException()
                }

                val userData = objectMapper
                    .readValue(response.body?.string(), UsersResponse::class.java)
                    .data?.getOrNull(0)

                return userData?.id?.toLong() ?: throw UserNoExistsException()
            }
    }

    class UserNoExistsException: Exception() {
        val command = "User does not exist"
    }
}