package com.twitch.nyquistbot.commands

import com.twitch.nyquistbot.model.BotProperties
import com.twitch.nyquistbot.model.ChatMessage
import com.twitch.nyquistbot.model.StreamService
import com.twitch.nyquistbot.model.UserService
import com.twitch.nyquistbot.transmission.Sender
import com.twitch.nyquistbot.utils.ResourcesContainer

class Sentence: Command() {
    override fun execute(chatMessage: ChatMessage, properties: BotProperties, sender: Sender) {
        try {
            val streamId = UserService.getUserID(chatMessage.channel, properties)
            val botUserId = UserService.getUserID(ResourcesContainer.configuration.api.twitch_nickname, properties)
            val moderators = StreamService.getStreamChatters(streamId, botUserId, properties)
        } catch (e: Exception) {
            println("ENGINE: Refuse to $call, not enough data")
            return
        }
        val sentence = "!tts natalia ty niepwoażna jesteś, dostałaś tyle pomysłów na" +
                " streamy to rób coś, wywalasz te szachy i jest tylko wiedźmin i gothic i oni" +
                " razem walczą przeciw sobie, gta też wywalasz bo sie zesrać można z tymi" +
                " częściami vice city, san francisco, czwórka piątka." +
                " grasz w csa dostajesz kijdropa, grasz w ets dostajesz umowe zlecenie na" +
                " jazde tirami, wystarczy tylko posłuchać widzów"
        sender.responseToMessage(chatMessage, sentence)
    }
}