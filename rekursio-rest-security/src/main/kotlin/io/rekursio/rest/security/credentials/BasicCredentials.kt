package io.rekursio.rest.security.credentials

/**
 * Basic authentication credentials.
 */
class BasicCredentials(
    val username: String,
    val password: String
) : Credentials