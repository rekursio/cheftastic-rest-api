package app.cheftastic.backend.data.di.module

import app.cheftastic.backend.data.source.DummyDataSource
import app.cheftastic.backend.data.source.DummyDataSourceImpl
import app.cheftastic.backend.data.source.DummyI18nDataSourceImpl
import dagger.Binds
import dagger.Module
import io.rekursio.rest.i18n.I18nDataSource

@Module
interface DataSourceModule {

    @Binds
    fun bindDummyDataSource(dataSource: DummyDataSourceImpl): DummyDataSource

    @Binds
    fun bindDummyI18nDataSource(dataSource: DummyI18nDataSourceImpl): I18nDataSource

}