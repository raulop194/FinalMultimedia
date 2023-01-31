package es.nexcreep.testing.ejercicio7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import es.nexcreep.testing.ejercicio7.databinding.ActivityDiceBinding
import kotlin.math.roundToInt
import kotlin.random.Random

class DiceActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rollDiceButton.setOnClickListener {
            val percentage = getPercentage()
            Log.v("DICE", "$percentage")
            startActivity(
                Intent(this, getNextActivityLayout(percentage))
            )
        }

    }

    private fun getPercentage(): Float {
        return Random.nextInt(1, 7) / 6f * 100f
    }

    private fun getNextActivityLayout(percentage: Float): Class<*> {
        return when {
            percentage.roundToInt() <= 25 -> EnemyActivity::class.java
            percentage.roundToInt() <= 50 -> CityActivity::class.java
            percentage.roundToInt() <= 75 -> TraderActivity::class.java
            percentage.roundToInt() <= 100 -> ObjectActivity::class.java
            else -> DiceActivity::class.java
        }
    }
}