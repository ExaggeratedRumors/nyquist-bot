package com.twitch.nyquistbot.transmission

import reactor.core.scheduler.Schedulers

class Receiver (private val connection: Connection) {

    fun configureReceiver(handleMessage: (String) -> (Unit)) {
        connection
            .queueFlux
            .metrics()
            .subscribeOn(
                Schedulers.parallel()
            )
            .subscribe {
                //if(!Activity.connection.isConnected()) return@subscribe
                handleMessage(it)
            }
    }
}