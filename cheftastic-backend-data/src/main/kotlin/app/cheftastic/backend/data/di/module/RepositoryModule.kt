package app.cheftastic.backend.data.di.module

import app.cheftastic.backend.data.repository.DummyRepository
import app.cheftastic.backend.data.repository.DummyRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindDummyRepository(repository: DummyRepositoryImpl): DummyRepository

}