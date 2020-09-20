package io.rekursio.rest.security

import io.ktor.application.*
import io.rekursio.rest.security.credentials.BasicCredentials
import io.rekursio.rest.security.credentials.BearerCredentials
import io.rekursio.rest.security.ktx.credentials
import org.slf4j.LoggerFactory

/**
 * Service in charge of authenticating all REST API calls.
 */
internal class SecurityService(private val authenticationService: AuthenticationService) {

    private val logger = LoggerFactory.getLogger(SecurityService::class.java)

    fun authenticate(call: ApplicationCall) {
        call.credentials
            .let { credentials ->
                when (credentials) {
                    is BasicCredentials -> {
                        performBasicAuthorization(credentials)
                    }

                    is BearerCredentials -> {
                        performBearerAuthorization(credentials)
                    }

                    else -> {
                        null
                    }
                }
            }
            ?.let { principal ->
                SecurityContext.from(call).principal(principal)
            }
    }

    private fun performBasicAuthorization(credentials: BasicCredentials): Principal? =
        authenticationService.authenticateWithUsernameAndPassword(credentials.username, credentials.password)

    private fun performBearerAuthorization(credentials: BearerCredentials): Principal? =
        authenticationService.authenticateWithToken(credentials.token)

}