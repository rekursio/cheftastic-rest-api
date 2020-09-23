package app.cheftastic.backend.domain.security.configuration

import io.rekursio.rest.security.RoleHierarchy

object Role {

    const val MASTER_CHIEF = "ROLE_MASTER_CHIEF"
    const val ADMIN = "ROLE_ADMIN"
    const val USER = "ROLE_USER"
    const val GUEST = "ROLE_GUEST"

    val roleHierarchy =
        RoleHierarchy.from(
            Pair(MASTER_CHIEF, arrayOf(ADMIN)),
            Pair(ADMIN, arrayOf(USER)),
            Pair(USER, arrayOf(GUEST))
        )
}