package example

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAutoConfiguration(
    exclude = [
        org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration::class,
        org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration::class
    ]
)
class SsrApplication

fun main(args: Array<String>) {
    runApplication<SsrApplication>(*args)
}
