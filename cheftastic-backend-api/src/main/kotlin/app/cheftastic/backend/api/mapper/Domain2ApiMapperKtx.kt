package app.cheftastic.backend.api.mapper

import app.cheftastic.backend.api.model.FooApiModel

fun String.toApi(): FooApiModel =
    FooApiModel(this)