package io.realworld

import dev.kilua.ssr.initSsr
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.compression.*

fun Application.main() {
    install(Compression)
    install(CachingHeaders) {
        options { call, content ->
            when (content.contentType?.withoutParameters()) {
                ContentType.Application.Wasm -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 3600))
                ContentType.Application.JavaScript -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 3600))
                ContentType.Text.Html -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 3600))
                else -> null
            }
        }
    }
    initSsr()
}
