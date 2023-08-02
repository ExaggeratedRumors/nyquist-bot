package com.twitch.nyquistbot.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class FollowResponse(
    val total: Int? = 0,
    val data: List<FollowData>? = listOf(),
    val pagination: Map<Any, Any>? = mapOf()
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class FollowData(
    val from_id: String? = String(),
    val from_login: String? = String(),
    val from_name: String? = String(),
    val to_id: String? = String(),
    val to_login: String? = String(),
    val to_name: String? = String(),
    val followed_at: String? = String()
)