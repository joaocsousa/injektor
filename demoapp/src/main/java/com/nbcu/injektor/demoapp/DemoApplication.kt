package com.nbcu.injektor.demoapp

import android.app.Application
import com.nbcu.injektor.singleton
import com.nbcu.injektor.transient

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        transient { DependencyA(label = "Dependency A") }
        singleton { DependencyB(label = "Dependency B") }
    }
}
