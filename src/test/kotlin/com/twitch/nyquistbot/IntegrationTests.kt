package com.twitch.nyquistbot

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            println(">> Setup")
        }

        @JvmStatic
        @AfterAll
        fun teardown() {
            println(">> Tear down")
        }
    }


    @Test
    fun `Assert blog page title, content and status code`() {
        println(">> Assert blog page title, content and status code")
//        val entity = restTemplate.getForEntity<String>("/")
//        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
//        assertThat(entity.body).contains("<h1>Blog</h1>")
    }

    @Test
    fun `Assert article page title, content and status code`() {
        println(">> TODO")
    }
}