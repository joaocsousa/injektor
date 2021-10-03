package com.nbcu.injektor

@PublishedApi
internal class Singleton<T>(private val provider: Provider<T>) : Provider<T> {
    private var instance: T? = null
    override operator fun invoke(): T {
        if (instance == null) {
            instance = provider()
        }
        return instance!!
    }
}
