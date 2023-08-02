package com.twitch.nyquistbot.transmission

import com.twitch.nyquistbot.model.BotProperties
import com.twitch.nyquistbot.utils.ResourcesContainer
import okhttp3.Request

class RequestBuilder {
    companion object {
        fun buildRequest(url: String, properties: BotProperties) = Request.Builder()
            .url(url)
            .addHeader("Client-ID", ResourcesContainer.configuration.api.twitch_client_id)
            .addHeader("Authorization", "Bearer ${properties.oauthToken}")
            .build()

        fun getFollowRequestUrl(idFrom: Long, idTo: Long) =
            "${ResourcesContainer.configuration.api.url}users/follows?from_id=${idFrom}&to_id=${idTo}"

        fun getUserRequestUrl(nickname: String) =
            "${ResourcesContainer.configuration.api.url}users?login=$nickname"
    }
}