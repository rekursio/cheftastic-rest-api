package app.cheftastic.backend.api.controller

import io.ktor.application.*

// TODO: To be removed. Just added to setup and test Dagger
interface DummyController {

    suspend fun foo(call: ApplicationCall)

}