package com.twitch.nyquistbot.transmission

import com.twitch.nyquistbot.model.BotActivity
import com.twitch.nyquistbot.model.Message
import reactor.core.scheduler.Schedulers

class Receiver (private val connection: Connection) {

    fun configureReceiver(handleMessage: (Message) -> (Unit)) {
        connection
            .queueFlux
            .subscribeOn(
                Schedulers.parallel()
            )
            .subscribe {
                if(!BotActivity.connection.isConnected()) return@subscribe
                handleMessage(Message(it))
            }
    }
}