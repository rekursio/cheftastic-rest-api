package io.rekursio.rest.security

import io.ktor.application.*
import io.ktor.util.*
import kotlin.properties.Delegates

/**
 * Holds the security context for any Ktor application call call.
 *
 * @param call instance of [ApplicationCall] to attach this context to.
 */
class SecurityContext private constructor(private val call: ApplicationCall) {

    companion object {
        private val AttributeKey = AttributeKey<SecurityContext>("SecurityContext")

        internal fun from(call: ApplicationCall) =
            call.attributes.computeIfAbsent(AttributeKey) { SecurityContext(call) }
    }

    var principal: Principal? by Delegates.vetoable<Principal?>(null) { _, old, _ ->
        require(old == null) { "Principal can only be assigned once" }
        true
    }

    inline fun <reified T : Principal> principal(): T? = principal as? T

    /**
     * Assigns the security context Principal value.
     *
     * @param principal Value to be set.
     */
    fun principal(principal: Principal) {
        this.principal = principal
    }
}