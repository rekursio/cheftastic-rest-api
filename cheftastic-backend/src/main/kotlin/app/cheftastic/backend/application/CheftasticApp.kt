package app.cheftastic.backend.application

import app.cheftastic.backend.application.di.authenticationService
import app.cheftastic.backend.application.di.dummyController
import app.cheftastic.backend.application.di.i18nDataSource
import app.cheftastic.backend.domain.security.configuration.Role
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.rekursio.rest.error.restErrors
import io.rekursio.rest.i18n.I18n
import io.rekursio.rest.security.Security
import io.rekursio.rest.security.ktx.hasRole

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.main() {

    // Ktor config
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
    install(Security) {
        service(authenticationService)
        roleHierarchy(Role.roleHierarchy)
    }
    install(I18n) {
        source(i18nDataSource)
    }
    install(ContentNegotiation) {
        json()
    }
    install(StatusPages) {
        restErrors()
    }

    routing {
        hasRole(Role.GUEST) {
            route("/") {
                get {
                    dummyController.foo(call)
                }
            }
        }
    }
}