package app.cheftastic.backend.domain.di

import app.cheftastic.backend.data.di.DataComponent
import app.cheftastic.backend.domain.di.module.ServiceModule
import app.cheftastic.backend.domain.dummy.service.DummyService
import dagger.Component
import io.rekursio.rest.i18n.I18nDataSource
import io.rekursio.rest.security.AuthenticationService

@DomainScope
@Component(
    modules = [
        ServiceModule::class
    ],
    dependencies = [
        DataComponent::class
    ]
)
interface DomainComponent {

    // Exposed dependencies to dependant components
    fun getDummyService(): DummyService

    fun getAuthenticationService(): AuthenticationService

    fun getI18nDataSource(): I18nDataSource

}