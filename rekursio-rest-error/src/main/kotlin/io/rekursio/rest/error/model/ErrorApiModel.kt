package io.rekursio.rest.error.model

import io.rekursio.rest.i18n.I18nField
import kotlinx.serialization.Serializable

@Serializable
internal class ErrorApiModel(
    val code: String,
    @I18nField
    val message: String
)