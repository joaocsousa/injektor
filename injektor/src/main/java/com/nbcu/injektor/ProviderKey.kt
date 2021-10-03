package com.nbcu.injektor

import kotlin.reflect.KClass

@PublishedApi
internal data class ProviderKey(val qualifier: String?, val tClass: KClass<*>)
