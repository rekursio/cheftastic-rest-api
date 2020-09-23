package app.cheftastic.backend.data.source

import javax.inject.Inject

// TODO: To be removed. Just added to setup and test Dagger
class DummyDataSourceImpl @Inject constructor() : DummyDataSource {

    override fun foo(): String =
        "Hello chef"

}