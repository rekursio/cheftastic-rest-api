package app.cheftastic.backend.data.source

import io.rekursio.rest.i18n.I18nDataSource
import java.util.*
import javax.inject.Inject

class DummyI18nDataSourceImpl @Inject constructor() : I18nDataSource {

    override fun getMessage(code: String, locale: Locale): String? =
        locale.toLanguageTag()

    override fun getMessage(code: String, args: Array<Any>, locale: Locale): String? =
        locale.toLanguageTag()
}