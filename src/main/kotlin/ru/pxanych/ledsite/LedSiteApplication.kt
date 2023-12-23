package ru.pxanych.ledsite

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import ru.pxanych.ledsite.service.LedControlService
import ru.pxanych.ledsite.web.LedControlWebsocketHandler

@SpringBootApplication
@EnableWebSocket
@Configuration
@Import(WebsocketConfig::class)
class LedSiteApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(LedSiteApplication::class.java, *args);
        }
    }

}