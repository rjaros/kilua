package example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SsrApplication

fun main(args: Array<String>) {
    runApplication<SsrApplication>(*args)
}
