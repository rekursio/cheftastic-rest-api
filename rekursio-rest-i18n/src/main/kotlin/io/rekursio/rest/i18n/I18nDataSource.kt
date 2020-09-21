package io.rekursio.rest.i18n

import java.util.*

/**
 * Data source that provides translation values.
 */
interface I18nDataSource {

    /**
     * Provides the translated message for the given [code] in the language specified by the [locale].
     *
     * @param code Message code.
     * @param locale Target language locale.
     *
     * @return Translated message or null if not found.
     */
    fun getMessage(code: String, locale: Locale): String?

    /**
     * Provides the translated message for the given [code] in the language specified by the [locale].
     *
     * @param code Message code.
     * @param default Default value to return.
     * @param locale Target language locale.
     *
     * @return Translated message or [default] value if not found.
     */
    fun getMessage(code: String, default: String, locale: Locale): String =
        getMessage(code, locale) ?: default

    /**
     * Provides the translated message for the given [code], formatted with the format [args] in the language specified
     * by the [locale].
     *
     * @param code Message code.
     * @param args Message format arguments.
     * @param locale Target language locale.
     *
     * @return Translated message or null if not found.
     */
    fun getMessage(code: String, args: Array<Any>, locale: Locale): String?

    /**
     * Provides the translated message for the given [code], formatted with the format [args] in the language specified
     * by the [locale].
     *
     * @param code Message code.
     * @param args Message format arguments.
     * @param default Default value to return.
     * @param locale Target language locale.
     *
     * @return Translated message or [default] value if not found.
     */
    fun getMessage(code: String, args: Array<Any>, default: String, locale: Locale): String =
        getMessage(code, args, locale) ?: default

}