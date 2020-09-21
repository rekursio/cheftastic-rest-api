package io.rekursio.rest.error.exception

import io.ktor.http.*

/**
 * Exception class that wraps any REST API io.rekursio.rest.error.exception.
 * All API exceptions should extend this class in order to be handled properly.
 *
 * In addition to the wrapped exception, it contains a {@link HttpStatusCode} code
 * and a message that will be returned as response.
 */
open class RestApiException(
    val status: Int = HttpStatusCode.InternalServerError.value,
    val code: String = ErrorCode.UNKNOWN_ERROR,
    message: String = HttpStatusCode.InternalServerError.description
) : RuntimeException(message)