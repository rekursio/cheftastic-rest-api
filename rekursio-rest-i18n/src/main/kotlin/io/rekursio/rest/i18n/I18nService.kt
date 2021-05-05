package io.rekursio.rest.i18n

import java.lang.reflect.Field
import java.util.*
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

/**
 * Internalization service that allows to translate the fields in any object annotated as [I18nField].
 *
 * @param dataSource Internalization data source used for obtaining localized messages.
 */
internal class I18nService(
    private val dataSource: I18nDataSource
) {

    /**
     * Translates the given [object] to the language specified by the [locale] value.
     *
     * @param object Object to be translated.
     * @param locale Target language locale value.
     *
     * @return A copy of the original object translated to the target language.
     */
    fun translate(`object`: Any, locale: Locale): Any {
        var result: Any

        try {
            if (`object` is Collection<*>) {
                result = translateCollection(`object`, locale)
            } else {
                result = copy(`object`)

                // Obtain all fields in the class and its hierarchy
                val fields = ArrayList<Field>()

                var `class`: Class<*>? = `object`::class.java
                while (`class` != null) {
                    fields.addAll(`class`.declaredFields)

                    // Navigate in the class hierarchy to add all its fields
                    `class` = `class`.superclass
                }

                fields.forEach { field ->
                    // Keep actual accessibility level
                    val isAccessible = field.isAccessible

                    // Set new value to the field
                    field.isAccessible = true
                    try {
                        field.set(result, translate(`object`, field, locale))
                    } catch (ignore: Throwable) {
                    }

                    // Restore actual accessibility level
                    field.isAccessible = isAccessible
                }
            }
        } catch (ignore: Throwable) {
            // If anything goes wrong just return the original value
            result = `object`
        }

        return result
    }

    /**
     * Translates all the objets in the given [collection] to the language specified by the [locale] value.
     *
     * @param collection Collection of objects to be translated.
     * @param locale Target language locale value.
     *
     * @return A copy of the original collection with all its elements translated to the target language.
     */
    private fun translateCollection(collection: Collection<*>, locale: Locale): List<*> {
        return collection.map { it?.let { translate(it, locale) } }
    }

    /**
     * Translated the [field] in the [target] object to the language specified by the [locale] value.
     *
     * @param target Object to be translated.
     * @param field Field in the [target] object to be translated.
     * @param locale Target language locale value.
     *
     * @return Field value translated.
     */
    private fun translate(target: Any?, field: Field?, locale: Locale): Any? {
        if (target == null || field == null) {
            return null
        }

        // Keep actual accessibility level
        val isAccessible = field.isAccessible

        // Get current field value
        field.isAccessible = true
        val value = field.get(target)

        // Restore actual accessibility level
        field.isAccessible = isAccessible

        return when {
            value is String -> {
                if (field.getAnnotationsByType(I18nField::class.java).isNotEmpty()) {
                    dataSource.getMessage(value, locale)
                } else {
                    value
                }
            }

            // Avoid recursive loops
            target.javaClass.name == value.javaClass.name || value.javaClass.name.startsWith("java.lang") -> {
                value
            }

            else -> {
                translate(value, locale)
            }
        }
    }

    /**
     * Creates a copy on an [object].
     *
     * @param object Object to be copied.
     *
     * @return Copy of the original [object]
     */
    private fun copy(`object`: Any): Any {
        var copy: Any? = null

        val functions = mutableListOf<KFunction<*>>()
            .apply {
                // First try with the copy functions
                addAll(`object`::class.declaredFunctions.filter { it.name == "copy" }.sortedBy { it.parameters.size })
                // Then try with the constructors
                addAll(`object`::class.constructors.sortedByDescending { it.parameters.size })
            }
            .iterator()

        while (copy == null && functions.hasNext()) {
            val function = functions.next()

            try {
                copy = if (function.parameters.isEmpty()) {
                    function.call()
                } else {
                    function.call(*getArguments(function, `object`))
                }
            } catch (t: Throwable) {
            }
        }

        if (copy == null) {
            copy = `object`
        }

        return copy
    }

    /**
     * Reads from the the [object] the value for [function] arguments.
     *
     * @param function Function to get the arguments for its invocation.
     * @param object Object from which obtain the function arguments.
     *
     * @return Function arguments obtained from the given object.
     */
    private fun getArguments(function: KFunction<*>, `object`: Any): Array<Any?> {
        return function.parameters
            .asSequence()
            // Transform into parameter names
            .map { it.name }
            // Obtain the fields in the `object`
            .map { name -> `object`::class.memberProperties.firstOrNull { it.name == name } }
            // Get values
            .map { field ->
                // Keep actual accessibility level
                val isAccessible = field?.isAccessible == true

                // Get current field value
                field?.isAccessible = true
                val value = field?.javaField?.get(`object`)

                // Restore actual accessibility level
                field?.isAccessible = isAccessible

                value
            }
            .toList()
            .toTypedArray()
    }
}