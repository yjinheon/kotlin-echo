package dev.echo.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody

@RestController
class BytesController {

    @GetMapping("/bytes", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun bytes(@RequestParam(defaultValue = "1048576") size: Long): ResponseEntity<StreamingResponseBody> {
        val max = 100L * 1024 * 1024 // 100MB
        val total = size.coerceIn(0, max)
        val chunk = ByteArray(8192) { 0 }

        val body = StreamingResponseBody { out ->
            var remaining = total
            while (remaining > 0) {
                val toWrite = if (remaining >= chunk.size) chunk.size else remaining.toInt()
                out.write(chunk, 0, toWrite)
                remaining -= toWrite
            }
            out.flush()
        }

        return ResponseEntity.ok()
            .header("Content-Length", total.toString())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(body)
    }
}
