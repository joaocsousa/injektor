package com.nbcu.injektor

import kotlin.reflect.KClass

data class DuplicateProviderFound(
    val qualifier: String?,
    val tClass: KClass<*>
) : IllegalStateException("duplicate provider for qualifier [$qualifier] - class [${tClass.simpleName}]")

data class NoProviderFound(
    val qualifier: String?,
    val tClass: KClass<*>
) : IllegalStateException("provider for for qualifier [$qualifier] - class [${tClass.simpleName}] not found")

object CircularDependencyException : IllegalStateException("circular dependency found")
