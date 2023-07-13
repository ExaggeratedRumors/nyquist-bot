package com.twitch.nyquistbot.model

import reactor.core.publisher.Flux
import java.net.Socket

class Connection (val host: String, val port: Int) {
    lateinit var queue : Flux<String>
    lateinit var socket: Socket

}