package com.twitch.nyquistbot.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HtmlController {
    @GetMapping("/test")
    fun testGet(
        @RequestParam(value = "input", defaultValue = "test") input: String
    ) = "Input value: $input"

}