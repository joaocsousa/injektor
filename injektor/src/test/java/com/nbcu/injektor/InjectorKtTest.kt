package com.nbcu.injektor

import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test

class InjectorKtTest {

    @Before
    fun setUp() {
        providers.clear()
        diStack.clear()
    }

    //region non-qualified injection tests
    @Test
    fun `Given a transient factory When injecting multiple times Then correct instances are returned`() {
        // Given
        val createdInstances = mutableListOf<Foo>()
        transient {
            Foo("label1").also { createdInstances.add(it) }
        }

        // When
        val instance1 = inject<Foo>()
        val instance2 = inject<Foo>()
        val instance3 = inject<Foo>()

        // Then
        assertEquals(3, createdInstances.size)
        assertSame(createdInstances[0], instance1)
        assertSame(createdInstances[1], instance2)
        assertSame(createdInstances[2], instance3)
    }

    @Test
    fun `Given a singleton factory When injecting multiple times Then correct instances are returned`() {
        // Given
        val createdInstances = mutableListOf<Foo>()
        singleton {
            Foo("label1").also { createdInstances.add(it) }
        }

        // When
        val instance1 = inject<Foo>()
        val instance2 = inject<Foo>()
        val instance3 = inject<Foo>()

        // Then
        assertEquals(1, createdInstances.size)
        assertSame(createdInstances[0], instance1)
        assertSame(createdInstances[0], instance2)
        assertSame(createdInstances[0], instance3)
    }
    //endregion

    //region qualified injection tests
    @Test
    fun `Given a transient factory with a qualifier When injecting Then correct instances are returned`() {
        // Given
        val createdInstances = mutableListOf<Foo>()
        transient(qualifier = "1") {
            Foo("label1").also { createdInstances.add(it) }
        }
        transient(qualifier = "2") {
            Foo("label2").also { createdInstances.add(it) }
        }

        // When
        val instance1 = inject<Foo>(qualifier = "1")
        val instance2 = inject<Foo>(qualifier = "2")
        val instance3 = inject<Foo>(qualifier = "1")
        val instance4 = inject<Foo>(qualifier = "2")

        // Then
        assertEquals(4, createdInstances.size)
        assertSame(createdInstances[0], instance1)
        assertSame(createdInstances[1], instance2)
        assertSame(createdInstances[2], instance3)
        assertSame(createdInstances[3], instance4)
    }

    @Test
    fun `Given a singleton factory with a qualifier When injecting Then correct instances are returned`() {
        // Given
        val createdInstances = mutableListOf<Foo>()
        singleton(qualifier = "1") {
            Foo("label1").also { createdInstances.add(it) }
        }
        singleton(qualifier = "2") {
            Foo("label2").also { createdInstances.add(it) }
        }

        // When
        val instance1 = inject<Foo>(qualifier = "1")
        val instance2 = inject<Foo>(qualifier = "2")
        val instance3 = inject<Foo>(qualifier = "1")
        val instance4 = inject<Foo>(qualifier = "2")

        // Then
        assertEquals(2, createdInstances.size)
        assertSame(createdInstances[0], instance1)
        assertSame(createdInstances[1], instance2)
        assertSame(createdInstances[0], instance3)
        assertSame(createdInstances[1], instance4)
    }
    //endregion

    //region exceptions tests
    @Test(expected = NoProviderFound::class)
    fun `Given a provider was not registered When injecting it Then exception is thrown`() {
        // When
        inject<Foo>()

        // Then (exception thrown)
    }

    @Test(expected = DuplicateProviderFound::class)
    fun `Given a transient provider is registered When registering the same provider Then exception is thrown`() {
        // Given
        transient { Foo(label = "some foo") }

        // When
        transient { Foo(label = "some foo ") }

        // Then (exception thrown)
    }

    @Test(expected = DuplicateProviderFound::class)
    fun `Given a singleton provider is registered When registering the same provider Then exception is thrown`() {
        // Given
        singleton { Foo(label = "some foo") }

        // When
        singleton { Foo(label = "some foo ") }

        // Then (exception thrown)
    }

    @Test(expected = DuplicateProviderFound::class)
    fun `Given a singleton provider is registered When registering the same transient provider Then exception is thrown`() {
        // Given
        singleton { Foo(label = "some foo") }

        // When
        transient { Foo(label = "some foo ") }

        // Then (exception thrown)
    }

    @Test(expected = CircularDependencyException::class)
    fun `Given a circular dependency When injecting Then exception is thrown`() {
        // Given
        transient { A(b = inject()) }
        transient { B(c = inject()) }
        transient { C(d = inject()) }
        transient { D(e = inject()) }
        transient { E(b = inject()) }

        // When
        inject<A>()

        // Then (exception thrown)
    }
    //endregion
}
