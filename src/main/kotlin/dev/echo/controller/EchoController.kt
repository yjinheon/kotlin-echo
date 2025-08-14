package dev.echo.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

data class EchoBody(
    val method: String,
    val path: String,
    val query: Map<String, String?>,
    val headers: Map<String, String>,
    val body: Any?,
    val timestamp: String = Instant.now().toString()
)


@RestController
@RequestMapping("/api")
class EchoController {

    @GetMapping("/health")
    fun hello() =  mapOf("status" to "ok", "ts" to Instant.now().toString())

    @RequestMapping("/reflect/**", method = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE])
    fun echo(req: HttpServletRequest, @RequestBody(required = false) body: Any?): EchoBody {
        val headers = req.headerNames.asSequence().associateWith { req.getHeader(it) }
        val query = req.parameterMap.mapValues { it.value.firstOrNull() }
        return EchoBody(
            method = req.method,
            path = req.requestURI,
            query = query,
            headers = headers,
            body = body
        )
    }

    @GetMapping("/delay/{millis}")
    fun delay(@PathVariable millis: Long): Map<String,Any>   {
        Thread.sleep(millis.coerceIn(0,30_000))
        return mapOf("delayedMs" to millis, "ts" to Instant.now().toString())
    }

    @GetMapping("/status/{code}")
    fun status(@PathVariable code: Int): ResponseEntity<Map<String, Any>> {
        val http = HttpStatus.resolve(code) ?: HttpStatus.INTERNAL_SERVER_ERROR
        return ResponseEntity.status(http).body(mapOf("code" to http.value(), "reason" to http.reasonPhrase))
    }

    @GetMapping("/headers")
    fun headers(req: HttpServletRequest): Map<String, String> =
        req.headerNames.asSequence().associateWith { req.getHeader(it) }

    @GetMapping("/json")
    fun json(@RequestParam(defaultValue = "world") name: String) =
        mapOf("message" to "hello, $name", "ts" to Instant.now().toString())
}
