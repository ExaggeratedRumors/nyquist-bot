package com.twitch.nyquistbot.transmission

import reactor.core.publisher.Flux
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Connection (private val host: String, private val port: Int) {
    lateinit var queueFlux : Flux<String>
    lateinit var socket: Socket
    lateinit var output : PrintWriter
    lateinit var input : BufferedReader

    fun start() : Boolean {
        try {
            socket = Socket(host, port)
            output = PrintWriter(socket.getOutputStream())
            input = BufferedReader(InputStreamReader(socket.getInputStream()))
            val inputStream = input.lines()
            this.queueFlux = Flux.fromStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
    fun send(message: String) {
        output.println(message)
    }
    fun isConnected() = socket.isConnected
}