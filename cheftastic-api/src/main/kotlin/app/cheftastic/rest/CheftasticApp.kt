package app.cheftastic.rest

import app.cheftastic.rest.controller.DummyController
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.main() {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost()
    }
    routing {
        route("/") {
            get {
                call.respond(DummyController.helloWorld())
            }
        }
    }
}