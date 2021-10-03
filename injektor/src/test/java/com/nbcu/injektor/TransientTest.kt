package com.nbcu.injektor

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertNotSame
import org.junit.Test

class TransientTest {

    private val provider = mockk<Provider<Foo>>()
    private val sut = Transient(provider)

    @Test
    fun `When sut invoked multiple times Then provider is called same number of times and different instances are returned`() {
        // Given
        every { provider() } answers { Foo("label") }

        // Whn
        val instance1 = sut.invoke()
        val instance2 = sut.invoke()
        val instance3 = sut.invoke()
        val instance4 = sut.invoke()

        // Then
        assertNotSame(instance1, instance2)
        assertNotSame(instance1, instance3)
        assertNotSame(instance1, instance4)
        verify(exactly = 4) { provider() }
    }
}
