package com.twitch.nyquistbot.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class UsersResponse(val data: List<UserData>? = null)

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserData(
    val id: String? = null,
    val login: String? = null,
    val display_name: String? = null,
    val type: String? = null,
    val broadcaster_type: String? = null,
    val description: String? = null,
    val profile_image_url: String? = null,
    val offline_image_url: String? = null,
    val view_count: String? = null,
    val created_at: String? = null
)