package io.rekursio.rest.error.exception

import io.ktor.http.*

/**
 * REST API exception that indicates that the requested resource is no longer available and no forwarding
 * address is known.
 *
 * This condition is expected to be considered permanent. If it is not possible to determine whether or not
 * the condition is permanent use {@link NotFoundException} instead.
 *
 * Throwing this exception returns a response to the client with {@link HttpStatusCode#Gone} status code.
 */
open class GoneException(
    code: String = ErrorCode.UNKNOWN_ERROR,
    message: String = HttpStatusCode.Gone.description
) : RestApiException(HttpStatusCode.Gone.value, code, message)