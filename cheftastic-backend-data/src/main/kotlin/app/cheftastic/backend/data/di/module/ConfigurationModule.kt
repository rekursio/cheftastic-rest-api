package app.cheftastic.backend.data.di.module

import app.cheftastic.backend.data.di.DataScope
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object ConfigurationModule {

    const val MONGODB_URI = "mongodb_uri"
    const val MONGODB_DATABASE = "mongodb_database"

    @Provides
    @DataScope
    fun provideConfiguration(): Config =
        ConfigFactory.load()

    @Provides
    @DataScope
    @Named(MONGODB_URI)
    fun provideMongodbUri(config: Config): String =
        config.getString("mongodb.uri")

    @Provides
    @DataScope
    @Named(MONGODB_DATABASE)
    fun provideDatabaseName(config: Config): String =
        config.getString("mongodb.database")

}