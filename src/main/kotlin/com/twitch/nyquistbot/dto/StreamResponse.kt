package com.twitch.nyquistbot.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class StreamResponse (
    val data: List<StreamData> = listOf(),
    val pagination: Map<String, String> = mapOf()
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class StreamData (
    val id: String? = null,
    val user_name: String? = null,
    val game_id: String? = null,
    val game_name: String? = null,
    val type: String? = null,
    val title: String? = null,
    val viewer_count: Int? = null,
    val started_at: String? = null,
    val language: String? = null,
    val thumbnail_url: String? = null,
    val tag_ids: List<String>? = null,
    val tags: List<String>? = null,
    val is_mature: Boolean? = null
)