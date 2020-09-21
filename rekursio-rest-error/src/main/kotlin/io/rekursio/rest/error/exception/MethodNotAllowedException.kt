package io.rekursio.rest.error.exception

import io.ktor.http.*

/**
 * REST API exception that indicates that the method specified in the request is not allowed for the resource.
 *
 * The response must include an {@code Allow} header containing a list of valid methods for the requested resource.
 *
 * Throwing this exception returns a response to the client with {@link HttpStatusCode#MethodNotAllowed} status code.
 */
open class MethodNotAllowedException(
    code: String = ErrorCode.UNKNOWN_ERROR,
    message: String = HttpStatusCode.MethodNotAllowed.description
) : RestApiException(HttpStatusCode.MethodNotAllowed.value, code, message)