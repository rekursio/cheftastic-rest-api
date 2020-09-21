package io.rekursio.rest.i18n.model

import io.rekursio.test.factory.ModelFactory
import io.rekursio.test.random.nextString
import kotlin.random.Random

object ClassAModelFactory : ModelFactory<ClassA> {

    override fun createOne(): ClassA =
        ClassA(
            Random.nextString(),
            Random.nextString(),
            Random.nextString()
        )
}