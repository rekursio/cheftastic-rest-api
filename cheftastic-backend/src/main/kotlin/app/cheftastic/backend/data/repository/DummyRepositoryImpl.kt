package app.cheftastic.backend.data.repository

import app.cheftastic.backend.data.source.DummyDataSource
import javax.inject.Inject

// TODO: To be removed. Just added to setup and test Dagger
class DummyRepositoryImpl @Inject constructor(
    private val dataSource: DummyDataSource
) : DummyRepository {

    override fun foo(): String =
        dataSource.foo()

}