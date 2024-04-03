package example

import dev.kilua.ssr.initSsr
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router

class MainVerticle : AbstractVerticle() {
    override fun start() {
        val router = Router.router(vertx)
        vertx.initSsr(router)
        val server = vertx.createHttpServer()
        server.requestHandler(router).listen(8080)
    }
}
