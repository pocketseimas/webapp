package com.pocketseimas.webapp

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong


@SpringBootApplication
@EnableCaching
@EnableScheduling
@RestController
class PocketSeimasApplication {

    val counter: AtomicLong = AtomicLong()

    @GetMapping("/hello")
    fun hello(@RequestParam(value = "name", defaultValue = "World") name: String): String {
        return String.format("Hello %s!", name)
    }

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): Greeting {
        return Greeting(counter.incrementAndGet(), String.format("Hello, %s!", name))
    }

    @RestController
    class TestController {
        @GetMapping("/test-error")
        fun testError(): String {
            throw RuntimeException("Test exception")
        }
    }

    @Bean
    fun commandLineRunner(ctx: ApplicationContext): CommandLineRunner {
        return CommandLineRunner { args: Array<String?>? ->
            println(ctx.id)
            println(ctx.applicationName)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<PocketSeimasApplication>(*args)
}
