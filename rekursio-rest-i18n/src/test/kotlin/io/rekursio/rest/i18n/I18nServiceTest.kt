package io.rekursio.rest.i18n

import com.nhaarman.mockitokotlin2.*
import io.rekursio.rest.i18n.model.*
import org.junit.Assert
import org.junit.Test
import java.util.*

class I18nServiceTest {

    private val translationMock = "TRANSLATED!"
    private val dataSourceMock = mock<I18nDataSource> {
        on { getMessage(any(), any()) }.then { translationMock }
        on { getMessage(any(), anyArray(), any()) }.then { translationMock }
    }

    private val service = I18nService(dataSourceMock)

    @Test
    fun `translate object without i18n fields`() {
        // given:
        val source = ClassAModelFactory.createOne()
        val locale = Locale.getDefault()

        // when:
        val result = service.translate(source, locale)

        // then:
        Assert.assertEquals(source, result)
        verifyZeroInteractions(dataSourceMock)
    }

    @Test
    fun `translate object with i18n field`() {
        // given:
        val source = ClassBModelFactory.createOne()
        val locale = Locale.getDefault()

        // when:
        val result = service.translate(source, locale) as? ClassB

        // then:
        Assert.assertNotNull(result)
        Assert.assertNotEquals(source, result)
        Assert.assertEquals(source.fieldA, result?.fieldA)
        Assert.assertEquals(source.fieldB, result?.fieldB)
        Assert.assertEquals(translationMock, result?.fieldC)
        verify(dataSourceMock, times(1)).getMessage(source.fieldC, locale)
        verifyNoMoreInteractions(dataSourceMock)
    }

    @Test
    fun `translate object with a collection field`() {
        // given:
        val source = ClassCModelFactory.createOne()
        val locale = Locale.getDefault()

        // when:
        val result = service.translate(source, locale) as? ClassC

        Assert.assertNotNull(result)
        Assert.assertNotEquals(source, result)
        Assert.assertEquals(source.fieldA, result?.fieldA)
        Assert.assertEquals(source.fieldB, result?.fieldB)
        result?.fieldC?.forEachIndexed { index, item ->
            Assert.assertEquals(source.fieldC.getOrNull(index)?.fieldA, item.fieldA)
            Assert.assertEquals(source.fieldC.getOrNull(index)?.fieldB, item.fieldB)
            Assert.assertEquals(translationMock, item.fieldC)
        }
        verify(dataSourceMock, times(source.fieldC.size)).getMessage(any(), eq(locale))
        verifyNoMoreInteractions(dataSourceMock)
    }

}