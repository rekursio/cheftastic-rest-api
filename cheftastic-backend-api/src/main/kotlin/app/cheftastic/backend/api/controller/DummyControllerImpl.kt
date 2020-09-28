package app.cheftastic.backend.api.controller

import app.cheftastic.backend.api.di.ApiScope
import app.cheftastic.backend.api.mapper.toApi
import app.cheftastic.backend.domain.dummy.service.DummyService
import io.ktor.application.*
import io.ktor.response.*
import javax.inject.Inject

// TODO: To be removed. Just added to setup and test Dagger
@ApiScope
class DummyControllerImpl @Inject constructor(
    private val service: DummyService
) : DummyController {

    override suspend fun foo(call: ApplicationCall) {
        call.respond(service.foo().toApi())
    }

}