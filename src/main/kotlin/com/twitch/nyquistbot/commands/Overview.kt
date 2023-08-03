package com.twitch.nyquistbot.commands

import com.fasterxml.jackson.databind.ObjectMapper
import com.twitch.nyquistbot.dto.UsersResponse
import com.twitch.nyquistbot.model.BotProperties
import com.twitch.nyquistbot.model.ChatMessage
import com.twitch.nyquistbot.transmission.BotRequest
import com.twitch.nyquistbot.transmission.Sender
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class Overview: Command() {
    override fun execute(chatMessage: ChatMessage, properties: BotProperties, sender: Sender) {
        if(properties.oauthToken == null) {
            println("ENGINE: Cannot execute $call, because OAuth token is null")
            return
        }
        val request = BotRequest.buildRequest(
            BotRequest.getUserRequestUrl(chatMessage.chatText),
            properties
        )

        BotRequest.executeRequest(request) { response ->
            if(!response.isSuccessful) {
                sender.responseToMessage(chatMessage , "@${chatMessage.author} account doesn't exist.")
                println("ENGINE: Refuse to $call non-existing user")
                return
            }

            val userData = ObjectMapper()
                .readValue(response.body?.string(), UsersResponse::class.java)
                .data?.getOrNull(0) ?: return

            userData.created_at?.let { date ->
                val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val period = Period.between(
                    LocalDate.parse(date.split("T")[0], dateFormat),
                    LocalDate.now()
                )
                val chatResponse = "Account created ${period.years} years, ${period.months} months " +
                        "and ${period.days} days from now"
                sender.responseToMessage(chatMessage, chatResponse)
            }
        }
    }
}