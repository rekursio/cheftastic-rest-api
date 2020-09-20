package io.rekursio.rest.security.error

import io.rekursio.rest.error.exception.ForbiddenException
import io.rekursio.rest.error.exception.UnauthorizedException

internal object Error {

    val INVALID_ACCESS_CREDENTIALS_EXCEPTION
        get() = UnauthorizedException(ErrorCode.INVALID_ACCESS_CREDENTIALS, ErrorMessage.INVALID_ACCESS_CREDENTIALS)

    val FORBIDDEN_ACCESS_EXCEPTION
        get() = ForbiddenException(ErrorCode.FORBIDDEN_ACCESS, ErrorMessage.FORBIDDEN_ACCESS)

}