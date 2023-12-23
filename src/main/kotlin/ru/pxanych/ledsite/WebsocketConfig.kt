package ru.pxanych.ledsite

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import ru.pxanych.ledsite.web.LedControlWebsocketHandler

@Configuration
class WebsocketConfig
@Autowired
constructor (
    val ledControlWebsocketHandler: LedControlWebsocketHandler
): WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(ledControlWebsocketHandler, "/ws")
    }

}