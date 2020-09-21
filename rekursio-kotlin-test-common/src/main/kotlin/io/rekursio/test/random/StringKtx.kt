package io.rekursio.test.random

import kotlin.random.Random

private val CHAR_POOL = ('a'..'z') + ('A'..'Z') + ('0'..'9')

/**
 * Get the next random string value with the specified [length].
 *
 * @param length String length.
 *
 * @return Random string value.
 */
fun Random.nextString(length: Int = 16): String =
    (1..length)
        .map { Random.nextInt(0, CHAR_POOL.size) }
        .map(CHAR_POOL::get)
        .joinToString("")