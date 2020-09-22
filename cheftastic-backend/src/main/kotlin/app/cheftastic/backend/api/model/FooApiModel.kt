package app.cheftastic.backend.api.model

import kotlinx.serialization.Serializable

// TODO: To be removed. Just added to setup and test Dagger
@Serializable
data class FooApiModel(
    val foo: String
)