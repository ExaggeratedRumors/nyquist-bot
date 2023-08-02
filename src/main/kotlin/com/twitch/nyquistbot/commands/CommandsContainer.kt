package com.twitch.nyquistbot.commands

class CommandsContainer {
    companion object {
        val implementedCommands = listOf(
            Echo(),
            Overview(),
            RandomPhrase(),
            Statistics(),
            FollowCheck(),
            Sentence()
        )
    }
}