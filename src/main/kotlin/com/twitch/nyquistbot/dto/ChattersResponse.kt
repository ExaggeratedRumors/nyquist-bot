package com.twitch.nyquistbot.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
class ChattersResponse (
    val data: List<ChattersData>? = null,
    val pagination: Map<Any?, Any?>? = null,
    val total: Int = 0
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
class ChattersData (
    val user_id: String? = null,
    val user_login: String? = null,
    val user_name: String? = null
)