package io.rekursio.rest.i18n.model

import io.rekursio.rest.i18n.I18nField

data class ClassB(
    val fieldA: String,
    val fieldB: String,
    @I18nField
    val fieldC: String
)