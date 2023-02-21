package es.nexcreep.testing.youradventure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.nexcreep.testing.youradventure.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}