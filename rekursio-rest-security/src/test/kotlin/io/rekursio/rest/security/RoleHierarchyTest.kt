package io.rekursio.rest.security

import org.junit.Assert
import org.junit.Test


class RoleHierarchyTest {

    companion object {
        const val ROLE_A = "ROLE_A"
        const val ROLE_B = "ROLE_B"
        const val ROLE_C = "ROLE_C"
        const val ROLE_D = "ROLE_D"
        const val ROLE_E = "ROLE_E"
        const val ROLE_F = "ROLE_F"
        const val ROLE_G = "ROLE_G"
        const val ROLE_H = "ROLE_H"
    }

    @Test
    fun `current role is the expected role`() {
        // given:
        val roleHierarchy =
            RoleHierarchy.from(
                Pair(ROLE_A, emptyArray())
            )

        // when:
        val hasRole = roleHierarchy.hasRole(ROLE_A, ROLE_A)

        // then:
        Assert.assertTrue(hasRole)
    }

    @Test
    fun `current role has one implied role and is the expected role`() {
        // given:
        val roleHierarchy =
            RoleHierarchy.from(
                Pair(ROLE_A, arrayOf(ROLE_B)),
                Pair(ROLE_B, emptyArray())
            )

        // when:
        val hasRole = roleHierarchy.hasRole(ROLE_A, ROLE_B)

        // then:
        Assert.assertTrue(hasRole)
    }

    @Test
    fun `current role has one implied role and it is not the expected role`() {
        // given:
        val roleHierarchy =
            RoleHierarchy.from(
                Pair(ROLE_A, arrayOf(ROLE_C)),
                Pair(ROLE_B, emptyArray())
            )

        // when:
        val hasRole = roleHierarchy.hasRole(ROLE_A, ROLE_B)

        // then:
        Assert.assertFalse(hasRole)
    }

    @Test
    fun `current role has many implied roles and the expected role is one of them`() {
        // given:
        val roleHierarchy =
            RoleHierarchy.from(
                Pair(ROLE_A, arrayOf(ROLE_B, ROLE_C, ROLE_D))
            )

        // when:
        val hasRole =
            roleHierarchy.hasRole(ROLE_A, ROLE_B)
                    && roleHierarchy.hasRole(ROLE_A, ROLE_C)
                    && roleHierarchy.hasRole(ROLE_A, ROLE_D)

        // then:
        Assert.assertTrue(hasRole)
    }

    @Test
    fun `current role has many implied roles and the expected role is not any of them`() {
        // given:
        val roleHierarchy =
            RoleHierarchy.from(
                Pair(ROLE_A, arrayOf(ROLE_B, ROLE_C, ROLE_D)),
                Pair(ROLE_E, emptyArray())
            )

        // when:
        val hasRole = roleHierarchy.hasRole(ROLE_A, ROLE_E)

        // then:
        Assert.assertFalse(hasRole)
    }

    @Test
    fun `current role has one implied role and the expected role is an implied role of it`() {
        // given:
        val roleHierarchy =
            RoleHierarchy.from(
                Pair(ROLE_A, arrayOf(ROLE_B)),
                Pair(ROLE_B, arrayOf(ROLE_C, ROLE_D, ROLE_E))
            )

        // when:
        val hasRole =
            roleHierarchy.hasRole(ROLE_A, ROLE_C)
                    && roleHierarchy.hasRole(ROLE_A, ROLE_D)
                    && roleHierarchy.hasRole(ROLE_A, ROLE_E)

        // then:
        Assert.assertTrue(hasRole)
    }

    @Test
    fun `current role has one implied role and the expected role is not an implied role of it`() {
        // given:
        val roleHierarchy =
            RoleHierarchy.from(
                Pair(ROLE_A, arrayOf(ROLE_B)),
                Pair(ROLE_B, arrayOf(ROLE_C, ROLE_D, ROLE_E)),
                Pair(ROLE_F, emptyArray())
            )

        // when:
        val hasRole = roleHierarchy.hasRole(ROLE_A, ROLE_F)

        // then:
        Assert.assertFalse(hasRole)
    }

    @Test
    fun `current role has many implied roles and the expected role is an implied role of one of them`() {
        // given:
        val roleHierarchy =
            RoleHierarchy.from(
                Pair(ROLE_A, arrayOf(ROLE_B, ROLE_C, ROLE_D)),
                Pair(ROLE_B, arrayOf(ROLE_E)),
                Pair(ROLE_C, arrayOf(ROLE_F)),
                Pair(ROLE_D, arrayOf(ROLE_G))
            )

        // when:
        val hasRole =
            roleHierarchy.hasRole(ROLE_A, ROLE_E)
                    && roleHierarchy.hasRole(ROLE_A, ROLE_F)
                    && roleHierarchy.hasRole(ROLE_A, ROLE_G)

        // then:
        Assert.assertTrue(hasRole)
    }

    @Test
    fun `current role has many implied roles and the expected role is not an implied role of any of them`() {
        // given:
        val roleHierarchy =
            RoleHierarchy.from(
                Pair(ROLE_A, arrayOf(ROLE_B, ROLE_C, ROLE_D)),
                Pair(ROLE_B, arrayOf(ROLE_E)),
                Pair(ROLE_C, arrayOf(ROLE_F)),
                Pair(ROLE_D, arrayOf(ROLE_G)),
                Pair(ROLE_H, emptyArray())
            )

        // when:
        val hasRole = roleHierarchy.hasRole(ROLE_A, ROLE_H)

        // then:
        Assert.assertFalse(hasRole)
    }
}