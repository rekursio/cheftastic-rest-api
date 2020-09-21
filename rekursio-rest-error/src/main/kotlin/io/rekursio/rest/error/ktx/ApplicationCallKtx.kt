package io.rekursio.rest.error.ktx

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.rekursio.rest.error.exception.ErrorCode
import io.rekursio.rest.error.exception.RestApiException
import io.rekursio.rest.error.model.ErrorApiModel

/**
 * Make the ApplicationCall fail responding with a controlled REST API error.
 */
internal suspend fun ApplicationCall.fail(throwable: Throwable) =
    when (throwable) {
        is RestApiException -> {
            respond(
                HttpStatusCode.fromValue(throwable.status),
                ErrorApiModel(throwable.code, throwable.message ?: "")
            )
        }

        else -> {
            respond(
                HttpStatusCode.InternalServerError,
                ErrorApiModel(ErrorCode.UNKNOWN_ERROR, throwable.message ?: HttpStatusCode.InternalServerError.description)
            )
        }
    }
