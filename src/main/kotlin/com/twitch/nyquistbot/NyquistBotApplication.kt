package com.twitch.nyquistbot

import com.twitch.nyquistbot.model.Activity
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NyquistBotApplication

fun main(args: Array<String>) {
    runApplication<NyquistBotApplication>(*args)
    Activity.startActivity()
    Activity.authorizeActivity()
}