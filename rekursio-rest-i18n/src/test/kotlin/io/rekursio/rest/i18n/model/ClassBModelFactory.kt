package io.rekursio.rest.i18n.model

import io.rekursio.test.factory.ModelFactory
import io.rekursio.test.random.nextString
import kotlin.random.Random

object ClassBModelFactory : ModelFactory<ClassB> {

    override fun createOne(): ClassB =
        ClassB(
            Random.nextString(),
            Random.nextString(),
            Random.nextString()
        )
}