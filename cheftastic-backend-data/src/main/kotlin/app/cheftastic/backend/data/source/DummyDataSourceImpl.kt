package app.cheftastic.backend.data.source

import app.cheftastic.backend.data.di.DataScope
import app.cheftastic.backend.data.model.Foo
import app.cheftastic.backend.data.source.mongodb.MongoCollectionProvider
import app.cheftastic.backend.data.source.mongodb.ktx.mongoCollections
import org.litote.kmongo.findOne
import javax.inject.Inject

// TODO: To be removed. Just added to setup and test Dagger
@DataScope
class DummyDataSourceImpl @Inject constructor(
    private val provider: MongoCollectionProvider
) : DummyDataSource {

    private val collection by mongoCollections<Foo>(provider)

    override fun foo(): String {
        return collection.findOne()?.foo ?: "Hello Chef!"
    }

}