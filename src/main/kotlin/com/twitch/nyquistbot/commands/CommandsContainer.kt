package com.twitch.nyquistbot.commands

class CommandsContainer {
    companion object {
        val implementedCommands = listOf(
            Echo(),
            Overview(),
            RandomPhrase(),
            FollowCheck(),
            Sentence()
        )
    }
}