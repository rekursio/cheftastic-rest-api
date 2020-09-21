package io.rekursio.rest.error

import io.ktor.application.*
import io.ktor.features.*
import io.rekursio.rest.error.ktx.fail

/**
 * Ktor StatusPages configuration for handling REST errors.
 */
fun StatusPages.Configuration.restErrors() {
    exception<Throwable> { throwable ->
        call.fail(throwable)
    }
}