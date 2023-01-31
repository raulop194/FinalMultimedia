package es.nexcreep.testing.ejercicio7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.nexcreep.testing.ejercicio7.databinding.ActivityEnemyBinding

class EnemyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnemyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnemyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actionA.setOnClickListener {
            startActivity(Intent(this, TodoActivity::class.java))
        }
        binding.actionB.setOnClickListener {
            startActivity(Intent(this, DiceActivity::class.java))
        }

    }
}