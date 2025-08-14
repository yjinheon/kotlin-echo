package dev.echo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinEchoApplication

fun main(args: Array<String>) {
    runApplication<KotlinEchoApplication>(*args)
}
