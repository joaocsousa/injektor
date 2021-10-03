package com.nbcu.injektor

import kotlin.random.Random

data class Foo(val label: String, val id: Long = Random.nextLong())
data class Bar(val label: String, val id: Long = Random.nextLong())

/**
 * Biz -> Baz -> Qux
 */
data class Biz(val baz: Baz, val id: Long = Random.nextLong())
data class Baz(val qux: Qux, val id: Long = Random.nextLong())
data class Qux(val label: String, val id: Long = Random.nextLong())

/**
 *    A -> B -> C
 *         ^    |
 *        |     v
 *        E <-  D
 */

data class A(val b: B, val id: Long = Random.nextLong())
data class B(val c: C, val id: Long = Random.nextLong())
data class C(val d: D, val id: Long = Random.nextLong())
data class D(val e: E, val id: Long = Random.nextLong())
data class E(val b: B, val id: Long = Random.nextLong())
