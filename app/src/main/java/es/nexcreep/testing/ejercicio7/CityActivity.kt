package es.nexcreep.testing.ejercicio7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.nexcreep.testing.ejercicio7.databinding.ActivityCityBinding

class CityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cityActionA.setOnClickListener {
            startActivity(Intent(this, TodoActivity::class.java))
        }
        binding.cityActionB.setOnClickListener {
            startActivity(Intent(this, DiceActivity::class.java))
        }
    }
}