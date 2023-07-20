package com.twitch.nyquistbot.transmission

import reactor.core.scheduler.Schedulers

class Receiver (private val connection: Connection) {

    fun configureReceiver(packageService: (String) -> (Unit)) {
        connection
            .queueFlux
            .metrics()
            .subscribeOn(
                Schedulers.parallel()
            )
            .subscribe {
                packageService(it)
            }
    }
}