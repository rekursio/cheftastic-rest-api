package app.cheftastic.backend.data.di.module

import app.cheftastic.backend.data.di.DataScope
import app.cheftastic.backend.data.model.Foo
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import javax.inject.Named

@Module
object DatabaseModule {

    @Provides
    @DataScope
    fun provideDatabaseClient(@Named(ConfigurationModule.MONGODB_URI) mongodbUri: String): MongoClient =
        KMongo.createClient(mongodbUri)

    @Provides
    @DataScope
    fun provideDatabase(
        client: MongoClient,
        @Named(ConfigurationModule.MONGODB_DATABASE) database: String
    ): MongoDatabase =
        client.getDatabase(database)

    @Provides
    @DataScope
    @IntoMap
    @ClassKey(Foo::class)
    // TODO: To be removed. Just added to setup and test MongoDB
    fun provideFooCollection(database: MongoDatabase): MongoCollection<*> =
        database.getCollection<Foo>()

}