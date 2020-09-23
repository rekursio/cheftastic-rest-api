package app.cheftastic.backend.api.di

import app.cheftastic.backend.api.controller.DummyController
import app.cheftastic.backend.api.di.module.ControllerModule
import app.cheftastic.backend.domain.di.DomainComponent
import dagger.Component
import io.rekursio.rest.i18n.I18nDataSource
import io.rekursio.rest.security.AuthenticationService

@ApiScope
@Component(
    modules = [
        ControllerModule::class
    ],
    dependencies = [
        DomainComponent::class
    ]
)
interface ApiComponent {

    // Exposed dependencies to dependant components
    fun getDummyController(): DummyController

    fun getAuthenticationService(): AuthenticationService

    fun getI18nDataSource(): I18nDataSource

}