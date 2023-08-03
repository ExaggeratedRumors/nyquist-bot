package com.twitch.nyquistbot.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class UserTokenResponse(
    var access_token: String? = null,
    var expires_in: Long? = null,
    var refresh_token: String? = null,
    var scope: List<String>? = null,
    var token_type: String? = null
)
