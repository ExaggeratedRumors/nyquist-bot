package com.twitch.nyquistbot.model

import reactor.core.publisher.Flux
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Connection (val host: String, val port: Int) {
    lateinit var queue : Flux<String>
    lateinit var socket: Socket
    lateinit var output : PrintWriter
    lateinit var input : BufferedReader

    fun start() : Boolean {
        try {
            socket = Socket(host, port)
            output = PrintWriter(socket.getOutputStream())
            input = BufferedReader(InputStreamReader(socket.getInputStream()))
            val inputStream = input.lines()
            this.queue = Flux.fromStream(inputStream)
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