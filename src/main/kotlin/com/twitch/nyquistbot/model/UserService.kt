package com.twitch.nyquistbot.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.twitch.nyquistbot.commands.FollowCheck
import com.twitch.nyquistbot.dto.UsersResponse
import com.twitch.nyquistbot.transmission.BotRequest

class UserService {
    companion object {
        fun getUserID(nickname: String, properties: BotProperties): Long {
            if(nickname.isEmpty() || properties.oauthToken == null)
                throw FollowCheck.UserNoExistsException()

            val request = BotRequest.buildRequest(
                BotRequest.getUserRequestUrl(nickname),
                properties
            )

            return BotRequest.executeRequest(request) { response ->
                if(!response.isSuccessful) {
                    println("ENGINE: Refuse to find user ID")
                    throw FollowCheck.UserNoExistsException()
                }

                val userData = ObjectMapper()
                    .readValue(response.body?.string(), UsersResponse::class.java)
                    .data?.getOrNull(0)

                userData?.id?.toLong() ?: throw FollowCheck.UserNoExistsException()
            }
        }
    }
}