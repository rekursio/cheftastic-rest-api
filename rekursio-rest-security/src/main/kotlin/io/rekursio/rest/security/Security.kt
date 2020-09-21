package io.rekursio.rest.security

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import io.rekursio.rest.security.error.Error
import io.rekursio.rest.security.ktx.principal
import org.slf4j.LoggerFactory

/**
 * Security allows to authenticate and authorize requests within Ktor applications.
 */
class Security {

    private var securityService: SecurityService? = null
    private var roleHierarchy: RoleHierarchy? = null

    private val logger = LoggerFactory.getLogger(Security::class.java)

    /**
     * Security configuration
     */
    class Configuration {

        internal var authenticationService: AuthenticationService? = null
        internal var roleHierarchy: RoleHierarchy? = null

        fun service(service: AuthenticationService) {
            authenticationService = service
        }

        fun roleHierarchy(hierarchy: RoleHierarchy) {
            roleHierarchy = hierarchy
        }

    }

    /**
     * Authorized route selector.
     */
    class AuthorizedRouteSelector : RouteSelector(RouteSelectorEvaluation.qualityConstant) {

        override fun evaluate(context: RoutingResolveContext, segmentIndex: Int): RouteSelectorEvaluation {
            return RouteSelectorEvaluation.Constant
        }

    }

    /**
     * Security Ktor application feature.
     */
    companion object Feature : ApplicationFeature<Application, Configuration, Security> {

        override val key: AttributeKey<Security> = AttributeKey("Security")

        /**
         * Phase in that user is authenticated in the system.
         */
        val AuthenticationPhase: PipelinePhase = PipelinePhase("Authentication")

        /**
         * Phase in that the authenticated user is authorized to perform a request.
         */
        val AuthorizationPhase: PipelinePhase = PipelinePhase("Authorization")

        override fun install(pipeline: Application, configure: Configuration.() -> Unit): Security {
            return Security().apply {
                configure(configure)
            }
        }
    }

    private fun configure(block: Configuration.() -> Unit) {
        val config = Configuration().apply(block)
        securityService = config.authenticationService?.let { SecurityService(it) }
        roleHierarchy = config.roleHierarchy
    }

    internal fun interceptPipeline(pipeline: ApplicationCallPipeline, vararg roles: String) {
        require(roles.isNotEmpty()) { "At least one role should be specified" }

        // Authenticate request
        pipeline.insertPhaseAfter(ApplicationCallPipeline.Features, AuthenticationPhase)
        pipeline.intercept(AuthenticationPhase) {
            securityService?.authenticate(call) ?: throw Exception()
        }

        // Authorized request
        pipeline.insertPhaseAfter(AuthenticationPhase, AuthorizationPhase)
        pipeline.intercept(AuthorizationPhase) {
            if (roleHierarchy?.hasRole(call.principal<Principal>()?.getRoles(), roles.toList()) != true) {
                throw Error.FORBIDDEN_ACCESS_EXCEPTION
            }
        }
    }

}