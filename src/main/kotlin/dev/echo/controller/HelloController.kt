package dev.echo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant


@RestController
@RequestMapping("/test")
class HelloController {

    @GetMapping("/hello")
    fun helloTest() = mapOf("greet" to "hello",
        "ts" to Instant.now().toString())

}
