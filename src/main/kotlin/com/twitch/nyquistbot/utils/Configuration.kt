package com.twitch.nyquistbot.utils

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Configuration(
    val server: Server = Server(),
    val api: Api = Api(),
    val channels: List<String> = listOf(),
    val prefix: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Server(
    val host: String = String(),
    val port: Int = 0,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Api(
    val twitch_nickname: String = String(),
    val twitch_client_id: String = String(),
    val twitch_client_secret: String = String(),
    val oauth_password: String = String(),
    val access_token: String = String()
)