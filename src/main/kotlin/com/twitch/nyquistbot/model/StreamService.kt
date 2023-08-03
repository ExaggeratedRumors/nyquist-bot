package com.twitch.nyquistbot.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.twitch.nyquistbot.dto.ChattersResponse
import com.twitch.nyquistbot.transmission.BotRequest
import java.io.IOException

class StreamService {
    companion object {
        fun getStreamChatters(channelId: Long, moderatorId: Long, properties: BotProperties): ChattersResponse {
            if(properties.userAccessToken == null) throw IOException("User access token is null")
            val request = BotRequest.buildRequest(
                BotRequest.getChattersRequestUrl(channelId, moderatorId),
                properties.userAccessToken
            )

            BotRequest.executeRequest(request) { response ->
                if (!response.isSuccessful) {
                    println("ENGINE: Refuse to load chatters")
                    throw IOException("Cannot read chatters")
                }

                return ObjectMapper()
                    .readValue(response.body?.string(), ChattersResponse::class.java)
            }
        }

        fun getStreamModerators(channelId: Long, properties: BotProperties): ChattersResponse {
            if(properties.userAccessToken == null) throw IOException("User access token is null")
            val request = BotRequest.buildRequest(
                BotRequest.getModeratorsRequestUrl(channelId),
                properties.userAccessToken
            )

            BotRequest.executeRequest(request) { response ->
                if (!response.isSuccessful) {
                    println("ENGINE: Refuse to load moderators")
                    throw IOException("Cannot read moderators")
                }

                return ObjectMapper()
                    .readValue(response.body?.string(), ChattersResponse::class.java)
            }
        }
    }
}