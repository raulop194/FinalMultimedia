package es.nexcreep.testing.ejercicio7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.nexcreep.testing.ejercicio7.databinding.ActivityObjectBinding

class ObjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityObjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.objectActionA.setOnClickListener {
            startActivity(Intent(this, TodoActivity::class.java))
        }
        binding.objectActionB.setOnClickListener {
            startActivity(Intent(this, DiceActivity::class.java))
        }
    }
}