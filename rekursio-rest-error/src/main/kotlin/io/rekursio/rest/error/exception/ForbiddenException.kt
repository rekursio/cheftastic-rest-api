package io.rekursio.rest.error.exception

import io.ktor.http.*

/**
 * REST API exception that indicates that the server understood the request but is refusing to fulfill it.
 *
 * Throwing this exception returns a response to the client with {@link HttpStatusCode#Forbidden} status code.
 */
open class ForbiddenException(
    code: String = ErrorCode.UNKNOWN_ERROR,
    message: String = HttpStatusCode.Forbidden.description
) : RestApiException(HttpStatusCode.Forbidden.value, code, message)