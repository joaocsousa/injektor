package com.nbcu.injektor

@PublishedApi
internal class Transient<T>(private val provider: Provider<T>) : Provider<T> by provider
