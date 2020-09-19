package io.rekursio.rest.error.exception

import io.ktor.http.*

/**
 * REST API exception that indicates a bad client request that could not be understood by the server due to
 * malformed syntax. The client should not repeat the request without modifications.
 *
 * Throwing this exception returns a response to the client with {@link HttpStatusCode#BadRequest} status code.
 */
open class BadRequestException(
    code: String = ErrorCode.UNKNOWN_ERROR,
    message: String = HttpStatusCode.BadRequest.description
) : RestApiException(HttpStatusCode.BadRequest.value, code, message)