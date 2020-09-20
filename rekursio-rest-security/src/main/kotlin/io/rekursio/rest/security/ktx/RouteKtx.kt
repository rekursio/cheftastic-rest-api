package io.rekursio.rest.security.ktx

import io.ktor.application.*
import io.ktor.routing.*
import io.rekursio.rest.security.Security

/**
 * Creates a secured route that only the specified roles can access to.
 *
 * @param roles Role names that have access to the route.
 * @param build Ktor REST API route.
 * @return Secured route.
 */
fun Route.hasRole(vararg roles: String, build: Route.() -> Unit): Route {
    require(roles.isNotEmpty())

    val authorizedRoute = createChild(Security.AuthorizedRouteSelector())
    application.feature(Security).interceptPipeline(authorizedRoute, *roles)
    authorizedRoute.build()

    return authorizedRoute
}