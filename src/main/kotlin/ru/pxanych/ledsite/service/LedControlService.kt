package ru.pxanych.ledsite.service

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.pxanych.ledsite.model.LedColor
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.concurrent.ConcurrentLinkedQueue

@Service
class LedControlService
@Autowired
constructor(
    @Value("\${led.controller.address}") addr: String,
    @Value("\${led.controller.port}") val port: Int,
    @Value("\${led.controller.update.interval.ms}") val updateInterval: Long,
) {

    private val log = LoggerFactory.getLogger(LedControlService::class.java)

    private final val socket = DatagramSocket()
    private final val dstAddress = InetAddress.getByName(addr)
    private final val updateQueue = ConcurrentLinkedQueue<List<LedColor>>()
    private lateinit var updaterThread: Thread

    @PostConstruct
    fun onStartup() {
        updaterThread = Thread(this::threadWorker)
        updaterThread.start()
    }

    @PreDestroy
    fun onShutdown() {
        socket.close()
    }

    fun submitUpdate(leds: List<LedColor>) {
        updateQueue.offer(leds)
    }

    private fun threadWorker() {
        while (true) {
            updateQueue.poll()?.apply { doSendUpdate(this) }
            Thread.sleep(updateInterval)
        }
    }

    private fun doSendUpdate(leds: List<LedColor>) {
        log.info("sending update for ${leds.size} leds")
        val data = ByteArray(leds.size * 3 + 4) { 0 }
        leds.forEachIndexed { i, ledColor ->
            val offset = 4 + i * 3
            data[offset + 0] = ledColor.red.toByte()
            data[offset + 1] = ledColor.green.toByte()
            data[offset + 2] = ledColor.blue.toByte()
        }
        socket.send(DatagramPacket(data, data.size, dstAddress, port))
    }

}
