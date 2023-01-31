package es.nexcreep.testing.ejercicio7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.nexcreep.testing.ejercicio7.databinding.ActivityTraderBinding

class TraderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTraderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTraderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.traderActionA.setOnClickListener {
            startActivity(Intent(this, TodoActivity::class.java))
        }
        binding.traderActionB.setOnClickListener {
            startActivity(Intent(this, DiceActivity::class.java))
        }

    }
}