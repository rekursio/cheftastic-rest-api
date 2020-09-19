package io.rekursio.rest.error.exception

import io.ktor.http.*

/**
 * REST API exception that indicates that the server encountered an unexpected condition which prevented
 * it from fulfilling the request.
 *
 * Throwing this exception returns a response to the client with {@link HttpStatusCode#InternalServerError} status code.
 */
open class InternalServerErrorException(
    code: String = ErrorCode.UNKNOWN_ERROR,
    message: String = HttpStatusCode.InternalServerError.description
) : RestApiException(HttpStatusCode.InternalServerError.value, code, message)