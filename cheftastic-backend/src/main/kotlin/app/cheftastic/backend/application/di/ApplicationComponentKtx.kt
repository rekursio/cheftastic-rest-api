package app.cheftastic.backend.application.di

import app.cheftastic.backend.api.controller.DummyController
import app.cheftastic.backend.api.di.DaggerApiComponent
import app.cheftastic.backend.data.di.DaggerDataComponent
import app.cheftastic.backend.domain.di.DaggerDomainComponent
import io.rekursio.rest.i18n.I18nDataSource
import io.rekursio.rest.security.AuthenticationService

val applicationComponent = DaggerApplicationComponent.builder()
    .apiComponent(
        DaggerApiComponent.builder()
            .domainComponent(
                DaggerDomainComponent.builder()
                    .dataComponent(
                        DaggerDataComponent.builder()
                            .build()
                    ).build()
            ).build()
    ).build()

val authenticationService: AuthenticationService
    get() = applicationComponent.getAuthenticationService()

val i18nDataSource: I18nDataSource
    get() = applicationComponent.getI18nDataSource()

val dummyController: DummyController
    get() = applicationComponent.getDummyController()