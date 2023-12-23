package ru.pxanych.ledsite.web

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.AbstractWebSocketHandler
import ru.pxanych.ledsite.model.LedColor
import ru.pxanych.ledsite.service.LedControlService

@Component
class LedControlWebsocketHandler
@Autowired
constructor(
    val ledControlService: LedControlService
) : AbstractWebSocketHandler() {

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val update = message
            .payload
            .split('|')
            .map { ledSpec ->
                val colors = ledSpec.split(',')
                if (colors.size < 3) {
                    session.sendMessage(TextMessage("Invalid format: '${ledSpec}' should have 3 colors"))
                    return
                }
                try {
                    LedColor(
                        colors[0].toInt(),
                        colors[1].toInt(),
                        colors[2].toInt()
                    )
                } catch (e: Exception) {
                    session.sendMessage(TextMessage("Invalid format: ${e.message}"))
                    return
                }
            }
        ledControlService.submitUpdate(update)
    }

}