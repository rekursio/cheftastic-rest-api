package io.rekursio.rest.error.exception

import io.ktor.http.*

/**
 * REST API exception that indicates that the request could not be completed due to a conflict with the
 * current state of the resource.
 *
 * This exception only should be thrown in situations where it is expected that the user might be able to resolve
 * the conflict and resubmit the request.
 *
 * Throwing this exception returns a response to the client with {@link HttpStatusCode#Conflict} status code.
 */
open class ConflictException(
    code: String = ErrorCode.UNKNOWN_ERROR,
    message: String = HttpStatusCode.Conflict.description
) : RestApiException(HttpStatusCode.Conflict.value, code, message)