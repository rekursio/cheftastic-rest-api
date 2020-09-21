package io.rekursio.rest.security.credentials

/**
 * Bearer token credentials.
 */
class BearerCredentials(
    val token: String
) : Credentials