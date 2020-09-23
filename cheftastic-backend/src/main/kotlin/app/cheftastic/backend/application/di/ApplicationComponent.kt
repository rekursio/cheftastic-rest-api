package app.cheftastic.backend.application.di

import app.cheftastic.backend.api.controller.DummyController
import app.cheftastic.backend.api.di.ApiComponent
import dagger.Component
import io.rekursio.rest.i18n.I18nDataSource
import io.rekursio.rest.security.AuthenticationService

@AppScope
@Component(
    dependencies = [
        ApiComponent::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        fun apiComponent(apiComponent: ApiComponent): Builder

        fun build(): ApplicationComponent
    }

    // Component injectors
    fun getDummyController(): DummyController

    fun getAuthenticationService(): AuthenticationService

    fun getI18nDataSource(): I18nDataSource

}