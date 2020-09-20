package io.rekursio.rest.security

/**
 * Service that provides authentication in the system.
 */
interface AuthenticationService {

    /**
     * Authenticate an user by its username and password.
     *
     * @param username User unique username.
     * @param password User password.
     */
    fun authenticateWithUsernameAndPassword(username: String, password: String): Principal?

    /**
     * Authenticated an user by an access token.
     *
     * @param token User access token.
     */
    fun authenticateWithToken(token: String): Principal?

}