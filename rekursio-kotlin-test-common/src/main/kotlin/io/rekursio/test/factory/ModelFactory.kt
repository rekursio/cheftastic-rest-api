package io.rekursio.test.factory

import kotlin.random.Random

/**
 * Model factory used to create test model fixtures.
 */
interface ModelFactory<T> {

    /**
     * Create one model fixture.
     *
     * @return Model fixture.
     */
    fun createOne(): T

    /**
     * Create a collection with the specified [amount] of model fixtures.
     *
     * @return Collection of model fixtures.
     */
    fun createMany(amount: Int? = null): Collection<T> =
        (1 until (amount ?: Random.nextInt(2, 10))).map { createOne() }

}