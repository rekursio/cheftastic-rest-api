package io.rekursio.rest.error.exception

import io.ktor.http.*

/**
 * REST API exception that indicates that the request requires user authentication.
 *
 * Throwing this exception returns a response to the client with {@link HttpStatusCode#Unauthorized} status code.
 */
open class UnauthorizedException(
    code: String = ErrorCode.UNKNOWN_ERROR,
    message: String = HttpStatusCode.Unauthorized.description
) : RestApiException(HttpStatusCode.Unauthorized.value, code, message)