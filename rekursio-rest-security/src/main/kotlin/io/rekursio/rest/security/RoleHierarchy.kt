package io.rekursio.rest.security

import org.slf4j.LoggerFactory

/**
 * Holds the security role hierarchy that allows to inherit permissions from one role to another.
 */
class RoleHierarchy private constructor() {

    private val roles: MutableMap<String, Role> = mutableMapOf()
    private val logger = LoggerFactory.getLogger(RoleHierarchy::class.java)

    companion object {

        /**
         * Creates a role hierarchy from the provided pairs of parent role name and its implied roles.
         *
         * @param roles Pairs of role and its implied roles.
         * @return Role hierarchy for the provided configuration.
         */
        fun from(vararg roles: Pair<String, Array<String>>): RoleHierarchy =
            RoleHierarchy().apply {
                roles.forEach {
                    addRole(it.first, *it.second)
                }
            }
    }

    /**
     * Checks if a role has the required role. The required role can be the actual role itself or any
     * of its implied roles defined by the role hierarchy.
     *
     * @param actualRole Actual role.
     * @param requiredRole Required role.
     * @return true if the actual role or its implied roles are the required one or false otherwise.
     */
    internal fun hasRole(actualRole: String, requiredRole: String): Boolean {
        logger.trace("#hasRole > Checking '$actualRole' against '$requiredRole'")
        val role = findRoleOrCreate(actualRole)
        return when {
            role.name == requiredRole -> {
                logger.trace("#hasRole > '${role.name}' is '$requiredRole'. Stop searching")
                true
            }

            role.impliedRoles.isEmpty() -> {
                logger.trace("#hasRole > '${role.name}' is not '$requiredRole' and has no more descendants. Stop searching")
                false
            }

            else -> {
                logger.trace("#hasRole > '${role.name}' is not '$requiredRole' and has descendants. Keep searching")
                role.impliedRoles.firstOrNull { descendant -> descendant.hasRole(requiredRole) } != null
            }
        }
    }

    /**
     * Checks if any of the given roles has the required role. The required role can be the actual role itself or
     * any of its implied roles defined by the role hierarchy.
     *
     * @param actualRoles Actual roles.
     * @param requiredRoles Required roles.
     * @return true if any of the actual roles or its implied roles are any of the required ones or false otherwise.
     */
    internal fun hasRole(actualRoles: Collection<String>?, requiredRoles: Collection<String>?): Boolean {
        requiredRoles?.forEach { requiredRole ->
            actualRoles?.forEach { userRole ->
                if (hasRole(userRole, requiredRole)) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * Adds one role to the current role hierarchy.
     *
     * @param roleName Name of the role to add.
     * @param impliedRoles Roles that are implied from the current one.
     */
    private fun addRole(roleName: String, vararg impliedRoles: String) {
        logger.trace("#addRole > Adding '$roleName' with ${impliedRoles.contentToString()} implied roles")
        findRoleOrCreate(roleName).apply {
            addImpliedRoles(
                impliedRoles.map { impliedRole -> findRoleOrCreate(impliedRole) }
            )
        }
    }

    /**
     * Finds a role in the current hierarchy by its name or creates a new one if it is not yet present.
     *
     * @param roleName Name of the role to find in the hierarchy.
     * @return Role for the specified name.
     */
    private fun findRoleOrCreate(roleName: String): Role =
        roles.getOrPut(roleName, {
            logger.trace("#findRoleOrCreate > '$roleName' is not yet present in the hierarchy and therefore it will be created")
            Role(roleName)
        })

    /**
     * Holds the information required for a role.
     */
    private class Role(val name: String) {

        private val _impliedRoles: MutableSet<Role> = mutableSetOf()

        val impliedRoles: Set<Role>
            get() = _impliedRoles

        /**
         * Adds implied roles to the current one.
         *
         * @param roles Implied roles to add.
         */
        fun addImpliedRoles(roles: Collection<Role>) {
            _impliedRoles.addAll(roles)
        }
    }

    /**
     * Checks if the current role is the specified one or if it is any of its implied roles.
     *
     * @param role Role to compare with.
     * @return true if the actual role or its implied roles are the required one or false otherwise.
     */
    private fun Role.hasRole(role: String): Boolean =
        hasRole(name, role)
}