package io.rekursio.rest.security

/**
 * Authenticated principal information.
 */
interface Principal {

    /**
     * Returns the roles assigned to the principal.
     */
    fun getRoles(): List<String>

}