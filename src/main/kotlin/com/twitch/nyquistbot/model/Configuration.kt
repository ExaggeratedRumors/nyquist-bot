package com.twitch.nyquistbot.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Configuration(
    val server: Server = Server(),
    val api: Api = Api(),
    val channels: List<String> = listOf()
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
    val twitchClientId: String = String(),
    val twitchClientSecret: String = String(),
    val irc: String = String(),
    val accessToken: String = String()
)
