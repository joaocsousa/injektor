package com.nbcu.injektor

fun interface Provider<T> {
    operator fun invoke(): T
}
