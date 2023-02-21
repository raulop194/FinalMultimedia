package es.nexcreep.testing.youradventure

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import es.nexcreep.testing.youradventure.databinding.ActivityCityBinding
import es.nexcreep.testing.youradventure.model.Player
import kotlin.math.roundToInt
import kotlin.random.Random

class CityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCityBinding
    private lateinit var player: Player

    private val maxEnemyCount = 5

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        player = Gson().fromJson(intent.getStringExtra("PLAYER_OBJ")?:"{}", Player::class.java)

        binding.defeatedEnemies.text = "Enemigos vencidos: ${player.enemiesDefeated}"

        binding.cityActionA.setOnClickListener {
            startActivity(
                Intent(this, getNextActivityLayout(getPercentage()))
                    .putExtra("PLAYER_OBJ", Gson().toJson(player))
                    .putExtra("ORIGIN_CITY", true)
            )
        }
        binding.cityActionB.setOnClickListener {
            if (player.enemiesDefeated >= maxEnemyCount) {
                startActivity(
                    Intent(this, DiceActivity::class.java)
                        .putExtra("PLAYER_OBJ", Gson().toJson(player))
                )
                return@setOnClickListener
            }

            Toast.makeText(
                this@CityActivity,
                "Te faltan vencer a ${maxEnemyCount - player.enemiesDefeated} enemigos.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getPercentage(): Float {
        return Random.nextInt(1, 7) / 6f * 100f
    }

    private fun getNextActivityLayout(percentage: Float): Class<*> {
        return when {
            percentage.roundToInt() <= 33 -> TraderActivity::class.java
            percentage.roundToInt() <= 66 -> EnemyActivity::class.java
            percentage.roundToInt() <= 100 -> ObjectActivity::class.java
            else -> DiceActivity::class.java
        }
    }
}