package com.nbcu.injektor

import kotlin.reflect.KClass

@PublishedApi
internal val providers: MutableMap<ProviderKey, Provider<*>> = mutableMapOf()

@PublishedApi
internal val diStack: MutableList<KClass<*>> = mutableListOf()

/**
 * Registers a transient provider.
 * This provider will be used to instantiate and provide a new instance of the class, every time it is injected.
 */
inline fun <reified T : Any> transient(qualifier: String? = null, provider: Provider<T>) {
    val providerKey = ProviderKey(qualifier, T::class)
    if (providers.contains(providerKey)) throw DuplicateProviderFound(qualifier, T::class)
    providers[providerKey] = Transient(provider)
    println("registered transient provider for qualifier [$qualifier] - class [${T::class.simpleName}]")
}

/**
 * Registers a singleton provider.
 * This provider will instantiate an instance of the provided class once. Afterwards, every injection will
 * be provided with the same instance of that class.
 */
inline fun <reified T : Any> singleton(qualifier: String? = null, provider: Provider<T>) {
    val providerKey = ProviderKey(qualifier, T::class)
    if (providers.contains(providerKey)) throw DuplicateProviderFound(qualifier, T::class)
    providers[providerKey] = Singleton(provider)
    println("registered singleton provider for qualifier [$qualifier] - class [${T::class.simpleName}]")
}

inline fun <reified T : Any> inject(qualifier: String? = null): T = synchronized(diStack) {
    val providerKey = ProviderKey(qualifier, T::class)
    when {
        providers.containsKey(providerKey) -> {
            if (diStack.contains(T::class)) throw CircularDependencyException
            diStack.add(T::class)
            val instance = providers.getValue(providerKey).invoke() as T
            diStack.remove(T::class)
            instance
        }
        else -> throw NoProviderFound(qualifier, T::class)
    }
}
