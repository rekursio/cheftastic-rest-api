package io.rekursio.rest.i18n

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.*
import org.slf4j.LoggerFactory
import java.util.*

/**
 * I18n allows to easily translate objects within Ktor applications.
 */
class I18n {

    /**
     * I18n configuration.
     */
    class Configuration {

        internal var i18nDataSource: I18nDataSource? = null

        fun source(dataSource: I18nDataSource) {
            i18nDataSource = dataSource
        }

    }

    /**
     * I18n Ktor application feature.
     */
    companion object Feature : ApplicationFeature<ApplicationCallPipeline, Configuration, I18n> {

        override val key: AttributeKey<I18n> = AttributeKey("I18n")

        private val logger = LoggerFactory.getLogger(I18n::class.java)
        private var i18nService: I18nService? = null

        override fun install(pipeline: ApplicationCallPipeline, configure: Configuration.() -> Unit): I18n {
            configure(configure)
            interceptPipeline(pipeline)

            return I18n()
        }

        private fun configure(block: Configuration.() -> Unit) {
            i18nService = Configuration().apply(block).i18nDataSource?.let { I18nService(it) }
        }

        private fun interceptPipeline(pipeline: ApplicationCallPipeline) {
            pipeline.sendPipeline.intercept(ApplicationSendPipeline.Transform) { subject ->
                val locale = context.request.header(HttpHeaders.AcceptLanguage)
                    ?.let { ranges ->
                        try {
                            Locale.LanguageRange.parse(ranges).sortedByDescending { it.weight }
                                .map { range -> Locale.forLanguageTag(range.range) }
                                .firstOrNull()
                        } catch (t: Throwable) {
                            logger.info("Couldn't parse value for '${HttpHeaders.AcceptLanguage}: $ranges'. Using default locale instead")
                            null
                        }
                    }
                    ?: Locale.ENGLISH
                val translated = i18nService?.translate(subject, locale) ?: subject
                proceedWith(translated)
            }
        }
    }
}