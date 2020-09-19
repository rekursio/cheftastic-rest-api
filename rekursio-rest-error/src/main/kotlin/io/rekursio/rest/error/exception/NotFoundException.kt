package io.rekursio.rest.error.exception

import io.ktor.http.*

/**
 * REST API exception that indicates that the server has not found the requested resource. This exception does
 * not indicate whether the condition is temporary or permanent.
 *
 * Throwing this exception returns a response to the client with {@link HttpStatusCode#NotFound} status code.
 */
open class NotFoundException(
    code: String = ErrorCode.UNKNOWN_ERROR,
    message: String = HttpStatusCode.NotFound.description
) : RestApiException(HttpStatusCode.NotFound.value, code, message)