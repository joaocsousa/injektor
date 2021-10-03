package com.nbcu.injektor

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertSame
import org.junit.Test

class SingletonTest {

    private val provider = mockk<Provider<Foo>>()
    private val sut = Singleton(provider)

    @Test
    fun `When sut invoked multiple times Then provider is called once and same instance is returned`() {
        // Given
        every { provider() } answers { Foo("label") }

        // When
        val instance1 = sut.invoke()
        val instance2 = sut.invoke()
        val instance3 = sut.invoke()
        val instance4 = sut.invoke()

        // Then
        assertSame(instance1, instance2)
        assertSame(instance1, instance3)
        assertSame(instance1, instance4)
        verify(exactly = 1) { provider() }
    }
}

