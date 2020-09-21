package io.rekursio.rest.security.ktx

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.rekursio.rest.security.Principal
import io.rekursio.rest.security.SecurityContext
import io.rekursio.rest.security.credentials.BasicCredentials
import io.rekursio.rest.security.credentials.BearerCredentials
import io.rekursio.rest.security.credentials.Credentials
import io.rekursio.rest.security.error.Error
import java.util.*

private val BASIC_AUTHORIZATION_HEADER_REGEX = "(Basic )(.*)".toRegex()
private val BASIC_AUTHORIZATION_REGEX = ("(.*):(.*)").toRegex()
private val BEARER_AUTHORIZATION_HEADER_REGEX = "(Bearer )(.*)".toRegex()

/**
 * Credentials used to authenticated the current Ktor application call.
 */
val ApplicationCall.credentials: Credentials?
    get() {
        val authorization = request.header(HttpHeaders.Authorization)

        return when {
            authorization == null -> {
                null
            }

            BASIC_AUTHORIZATION_HEADER_REGEX.matches(authorization) -> {
                lateinit var username: String
                lateinit var password: String

                // Authenticate user by username and password
                BASIC_AUTHORIZATION_HEADER_REGEX.matchEntire(authorization)
                    ?.groups?.lastOrNull()?.value
                    // Decode Base64 credentials
                    ?.let { base64 ->
                        Base64.getDecoder().decode(base64).toString(Charsets.UTF_8)
                    }
                    // Read username and password
                    ?.let { credentials ->
                        BASIC_AUTHORIZATION_REGEX.matchEntire(credentials)?.let { result ->
                            username = result.groups[1]?.value ?: throw Error.INVALID_ACCESS_CREDENTIALS_EXCEPTION
                            password = result.groups[2]?.value ?: throw Error.INVALID_ACCESS_CREDENTIALS_EXCEPTION
                        }
                    }

                BasicCredentials(username, password)
            }

            BEARER_AUTHORIZATION_HEADER_REGEX.matches(authorization) -> {
                val token = BEARER_AUTHORIZATION_HEADER_REGEX.matchEntire(authorization)
                    ?.groups?.lastOrNull()?.value ?: throw Exception()

                BearerCredentials(token)
            }

            else -> {
                null
            }
        }
    }

/**
 * Security context attached to the current Ktor application call.
 */
val ApplicationCall.securityContext: SecurityContext
    get() = SecurityContext.from(this)

/**
 * Provides the authenticated principal info for the current Ktor application call.
 *
 * @return Authenticated principal info or null if not authenticated
 */
inline fun <reified T : Principal> ApplicationCall.principal(): T? = securityContext.principal()