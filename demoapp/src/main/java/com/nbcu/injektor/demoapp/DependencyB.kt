package com.nbcu.injektor.demoapp

import kotlin.random.Random

data class DependencyB(val label: String, val random: Int = Random.nextInt())
