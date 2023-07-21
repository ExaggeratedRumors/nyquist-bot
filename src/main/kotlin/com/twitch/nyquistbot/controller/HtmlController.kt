package com.twitch.nyquistbot.controller

import com.twitch.nyquistbot.commands.RandomPhrase
import com.twitch.nyquistbot.model.BotActivity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController {
    @GetMapping("/commands")
    fun commandsGet(model: Model): String {
        model["title"] = "Commands"
        model.addAttribute("commands", BotActivity.registeredCommands)
        return "commands"
    }

    @GetMapping("/phrases")
    fun phrasesGet(model: Model): String {
        model["title"] = "Phrases"
        model.addAttribute("phrases", RandomPhrase.phrases)
        return "phrases"
    }
}