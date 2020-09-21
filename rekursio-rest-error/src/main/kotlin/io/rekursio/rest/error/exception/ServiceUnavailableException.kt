package io.rekursio.rest.error.exception

import io.ktor.http.*

/**
 * REST API exception that indicates that the server is currently unable to handle the request due to a temporary
 * overloading or maintenance process.
 *
 * This exception should only be thrown to indicate a temporary condition which will be alleviated after some delay.
 * It the length of the delay is known it may be indicated in a {@code Retry-After} header.
 *
 * Throwing this exception returns a response to the client with {@link HttpStatusCode#ServiceUnavailable} status code.
 */
open class ServiceUnavailableException(
    code: String = ErrorCode.UNKNOWN_ERROR,
    message: String = HttpStatusCode.ServiceUnavailable.description
) : RestApiException(HttpStatusCode.ServiceUnavailable.value, code, message)