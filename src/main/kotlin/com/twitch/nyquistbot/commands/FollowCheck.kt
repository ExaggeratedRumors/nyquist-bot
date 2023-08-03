package com.twitch.nyquistbot.commands

import com.fasterxml.jackson.databind.ObjectMapper
import com.twitch.nyquistbot.dto.FollowResponse
import com.twitch.nyquistbot.model.BotProperties
import com.twitch.nyquistbot.model.ChatMessage
import com.twitch.nyquistbot.model.UserService
import com.twitch.nyquistbot.transmission.BotRequest
import com.twitch.nyquistbot.transmission.Sender
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class FollowCheck: Command() {
    private val objectMapper = ObjectMapper()

    override fun execute(chatMessage: ChatMessage, properties: BotProperties, sender: Sender) {
        if(properties.appAccessToken == null) {
            println("ENGINE: Cannot execute $call, because OAuth token is null")
            return
        }
        val splitChatText = chatMessage.chatText.split(" ")

        try {
            val idFrom = UserService.getUserID(splitChatText[0], properties)
            val idTo = UserService.getUserID(splitChatText[1], properties)
            val request = BotRequest.buildRequest(
                BotRequest.getFollowToRequestUrl(idFrom, idTo),
                properties.appAccessToken
            )

            BotRequest.executeRequest(request) { response ->
                if (!response.isSuccessful) {
                    println("ENGINE: Refuse to $call incorrect response")
                    throw UserNoExistsException()
                }

                val followData = objectMapper
                    .readValue(response.body?.string(), FollowResponse::class.java)
                if(followData.total == 1) {
                    sender.responseToMessage(chatMessage, "${splitChatText[0]} following" +
                            " ${splitChatText[1]} for ${getFollowDuration(followData)}")
                }
                else sender.responseToMessage(chatMessage, "${splitChatText[0]} isn't following ${splitChatText[1]}")
            }
        } catch (e: UserNoExistsException) {
            sender.responseToMessage(chatMessage , "@${chatMessage.author} account doesn't exist.")
            return
        }
    }

    private fun getFollowDuration(followData: FollowResponse): String {
        val period = followData.data?.get(0)?.followed_at.let {
            val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return@let Period.between(
                LocalDate.parse(it?.split("T")?.get(0), dateFormat),
                LocalDate.now()
            )
        } ?: return "an indefinite period"
        return if(period.years > 0)
            "${period.years} years and ${period.months} months"
        else if(period.months > 0)
            "${period.months} months and ${period.days} days"
        else if(period.days > 0)
            "${period.days} days"
        else "few hours"
    }

    class UserNoExistsException: Exception() {
        val command = "User does not exist"
    }
}