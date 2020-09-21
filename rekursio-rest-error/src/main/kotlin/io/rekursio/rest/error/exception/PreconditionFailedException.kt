package io.rekursio.rest.error.exception

import io.ktor.http.*

/**
 * REST API exception that indicates that a given precondition in one or more of the request headers was not met.
 *
 * This exception allows the client to place preconditions on the current resource meta-information and thus prevent
 * the requested method from being applied to a resource other than te one intended.
 *
 * Throwing this exception returns a response to the client with {@link HttpStatusCode#PreconditionFailed} status code.
 */
open class PreconditionFailedException(
    code: String = ErrorCode.UNKNOWN_ERROR,
    message: String = HttpStatusCode.PreconditionFailed.description
) : RestApiException(HttpStatusCode.PreconditionFailed.value, code, message)