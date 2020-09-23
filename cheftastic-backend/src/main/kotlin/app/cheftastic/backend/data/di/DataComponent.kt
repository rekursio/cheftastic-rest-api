package app.cheftastic.backend.data.di

import app.cheftastic.backend.data.di.module.DataSourceModule
import app.cheftastic.backend.data.di.module.RepositoryModule
import app.cheftastic.backend.data.repository.DummyRepository
import dagger.Component
import io.rekursio.rest.i18n.I18nDataSource

@DataScope
@Component(
    modules = [
        DataSourceModule::class,
        RepositoryModule::class
    ]
)
interface DataComponent {

    // Exposed dependencies to dependant components
    fun getDummyRepository(): DummyRepository

    fun getI18nDataSource(): I18nDataSource

}