package io.rekursio.rest.error.ktx

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.util.*
import io.rekursio.rest.error.RestErrors

/**
 * Ktor StatusPages configuration for handling REST errors.
 */
fun StatusPages.Configuration.restErrors() {
    exception<Throwable> { throwable ->
        RestErrors.logger.error(throwable)
        call.fail(throwable)
    }
}