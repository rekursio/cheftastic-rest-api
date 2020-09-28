package app.cheftastic.backend.data.source.mongodb

import app.cheftastic.backend.data.di.DataScope
import com.mongodb.client.MongoCollection
import javax.inject.Inject
import kotlin.reflect.KClass

@DataScope
class MongoCollectionProvider @Inject constructor(
    private val collections: @JvmSuppressWildcards Map<Class<*>, MongoCollection<*>>
) {

    fun <T : Any> get(`class`: KClass<T>): MongoCollection<T> {
        val collection =
            collections[`class`.java] ?: throw IllegalArgumentException("Unknown MongoCollection model class $`class`")
        try {
            @Suppress("UNCHECKED_CAST")
            return collection as MongoCollection<T>
        } catch (t: Throwable) {
            throw IllegalArgumentException("Cannot retrieve MongoCollection for model class $`class`")
        }
    }

}