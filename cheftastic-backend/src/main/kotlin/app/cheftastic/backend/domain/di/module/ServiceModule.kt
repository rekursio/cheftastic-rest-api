package app.cheftastic.backend.domain.di.module

import app.cheftastic.backend.domain.di.DomainScope
import app.cheftastic.backend.domain.dummy.service.DummyService
import app.cheftastic.backend.domain.dummy.service.DummyServiceImpl
import app.cheftastic.backend.domain.security.service.AuthenticationServiceImpl
import dagger.Binds
import dagger.Module
import io.rekursio.rest.security.AuthenticationService

@Module
interface ServiceModule {

    @Binds
    @DomainScope
    fun bindDummyService(service: DummyServiceImpl): DummyService

    @Binds
    @DomainScope
    fun bindAuthenticationService(service: AuthenticationServiceImpl): AuthenticationService

}