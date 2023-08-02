package com.twitch.nyquistbot

import com.twitch.nyquistbot.model.BotActivity
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NyquistBotApplication

fun main(args: Array<String>) {
    runApplication<NyquistBotApplication>(*args)
    BotActivity().startActivity()
}