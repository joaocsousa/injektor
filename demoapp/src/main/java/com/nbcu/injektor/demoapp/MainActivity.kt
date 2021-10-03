package com.nbcu.injektor.demoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nbcu.injektor.demoapp.databinding.ActivityMainBinding
import com.nbcu.injektor.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firstDepA = inject<DependencyA>()
        val secondDepA = inject<DependencyA>()

        // different instances
        Log.d(MainActivity::class.simpleName, "transient: $firstDepA - $secondDepA")

        val firstDepB = inject<DependencyB>()
        val secondDepB = inject<DependencyB>()

        // same instances
        Log.d(MainActivity::class.simpleName, "singleton $firstDepB - $secondDepB")
    }
}
