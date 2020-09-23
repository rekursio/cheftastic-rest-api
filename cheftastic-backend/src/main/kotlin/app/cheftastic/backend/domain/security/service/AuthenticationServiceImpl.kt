package app.cheftastic.backend.domain.security.service

import app.cheftastic.backend.domain.security.configuration.Role
import io.rekursio.rest.security.AuthenticationService
import io.rekursio.rest.security.Principal
import javax.inject.Inject

class AuthenticationServiceImpl @Inject constructor() : AuthenticationService {

    override fun authenticateWithUsernameAndPassword(username: String, password: String): Principal? =
        object : Principal {
            override fun getRoles(): List<String> {
                return listOf(Role.USER)
            }
        }

    override fun authenticateWithToken(token: String): Principal? =
        object : Principal {
            override fun getRoles(): List<String> {
                return listOf(Role.ADMIN)
            }
        }

}