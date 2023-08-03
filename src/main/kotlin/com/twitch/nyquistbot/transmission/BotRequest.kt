package com.twitch.nyquistbot.transmission

import com.twitch.nyquistbot.model.BotProperties
import com.twitch.nyquistbot.utils.ResourcesContainer
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class BotRequest {
    companion object {
        fun buildRequest(url: String, properties: BotProperties) = Request.Builder()
            .url(url)
            .addHeader("Client-ID", ResourcesContainer.configuration.api.twitch_client_id)
            .addHeader("Authorization", "Bearer ${properties.oauthToken}")
            .build()

        inline fun<R> executeRequest(request: Request, executor: (Response) -> (R)): R {
            val client = OkHttpClient()
            return client.newCall(request)
                .execute()
                .use { response -> executor(response) }
        }

        fun getFollowersOfRequestUrl(idTo: Long) =
            "${ResourcesContainer.configuration.api.url}users/follows?to_id=$idTo"
        fun getFollowToRequestUrl(idFrom: Long, idTo: Long) =
            "${ResourcesContainer.configuration.api.url}users/follows?from_id=$idFrom&to_id=$idTo"
        fun getUserRequestUrl(nickname: String) =
            "${ResourcesContainer.configuration.api.url}users?login=$nickname"
        fun getStreamRequestUrl(channel: String) =
            "${ResourcesContainer.configuration.api.url}streams?user_login=$channel"
        fun getChattersRequestUrl(channelId: Long, moderatorId: Long) =
            "${ResourcesContainer.configuration.api.url}chat/chatters?broadcaster_id=$channelId&moderator_id=$moderatorId"
        fun getModeratorsRequestUrl(channelId: Long) =
            "${ResourcesContainer.configuration.api.url}moderation/moderators?broadcaster_id=$channelId"
    }
}