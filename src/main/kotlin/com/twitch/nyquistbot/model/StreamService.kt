package com.twitch.nyquistbot.model

import com.twitch.nyquistbot.transmission.BotRequest
import java.io.IOException

class StreamService {
    companion object {
        fun getStreamChatters(channelId: Long, moderatorId: Long, properties: BotProperties) {
            val request = BotRequest.buildRequest(
                BotRequest.getChattersRequestUrl(channelId, moderatorId),
                properties
            )

            BotRequest.executeRequest(request) { response ->
                println(response.body?.string())
                if(!response.isSuccessful) {
                    println("ENGINE: Refuse to load chatters")
                    throw IOException("Cannot read chatters")
                }

                val chattersData = response.body?.string()
                println(chattersData)
            }
        }
    }
}