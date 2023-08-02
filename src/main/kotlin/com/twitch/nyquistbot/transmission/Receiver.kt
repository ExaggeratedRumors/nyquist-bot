package com.twitch.nyquistbot.transmission

import com.twitch.nyquistbot.model.ChatMessage
import reactor.core.scheduler.Schedulers

class Receiver (private val connection: Connection) {

    fun configureReceiver(handleMessage: (ChatMessage) -> (Unit)) {
        connection
            .queueFlux
            .subscribeOn(
                Schedulers.parallel()
            )
            .subscribe {
                if(!connection.isConnected()) return@subscribe
                handleMessage(ChatMessage(it))
            }
    }
}