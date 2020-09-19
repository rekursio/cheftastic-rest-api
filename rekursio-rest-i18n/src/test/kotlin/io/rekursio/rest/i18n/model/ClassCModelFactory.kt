package io.rekursio.rest.i18n.model

import io.rekursio.test.factory.ModelFactory
import io.rekursio.test.random.nextString
import kotlin.random.Random

object ClassCModelFactory : ModelFactory<ClassC> {

    override fun createOne(): ClassC =
        ClassC(
            Random.nextString(),
            Random.nextString(),
            ClassBModelFactory.createMany().toList()
        )
}