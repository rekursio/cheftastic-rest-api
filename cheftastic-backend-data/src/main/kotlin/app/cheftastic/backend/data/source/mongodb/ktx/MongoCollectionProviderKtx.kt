package app.cheftastic.backend.data.source.mongodb.ktx

import app.cheftastic.backend.data.source.mongodb.MongoCollectionProvider
import com.mongodb.client.MongoCollection

internal inline fun <reified T : Any> mongoCollections(provider: MongoCollectionProvider): Lazy<MongoCollection<T>> =
    object : Lazy<MongoCollection<T>> {

        private var cached: MongoCollection<T>? = null

        override val value: MongoCollection<T>
            get() = cached ?: provider.get(T::class).also { cached = it }

        override fun isInitialized(): Boolean =
            cached != null
    }