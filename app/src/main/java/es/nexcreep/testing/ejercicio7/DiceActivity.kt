package es.nexcreep.testing.ejercicio7

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import es.nexcreep.testing.ejercicio7.databinding.ActivityDiceBinding
import kotlin.math.roundToInt
import kotlin.random.Random

class DiceActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiceBinding
    lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        player = Gson().fromJson(intent.getStringExtra("PLAYER_OBJ")?:"{}", Player::class.java)
        Log.v("PLAYER_OBJ", "Dice: ${player.name}")
        Log.v("PLAYER_OBJ", "Dice: ${player.backpack}")

        genResume()

        binding.rollDiceButton.setOnClickListener {
            val percentage = getPercentage()
            Log.v("DICE", "$percentage")
            startActivity(
                Intent(this, CityActivity::class.java)
                    .putExtra("PLAYER_OBJ", Gson().toJson(player))
            )
        }

    }

    private fun getPercentage(): Float {
        return Random.nextInt(1, 7) / 6f * 100f
    }

    private fun getNextActivityLayout(percentage: Float): Class<*> {
        return when {
            percentage.roundToInt() <= 25 -> CityActivity::class.java
            percentage.roundToInt() <= 50 -> TraderActivity::class.java
            percentage.roundToInt() <= 75 -> EnemyActivity::class.java
            percentage.roundToInt() <= 100 -> ObjectActivity::class.java
            else -> DiceActivity::class.java
        }
    }

    @SuppressLint("SetTextI18n")
    private fun genResume() {
        binding.resumeDiceName.text = player.name
        binding.resumeDiceRace.text = player.playerRace
        binding.resumeDiceClass.text = player.playerClass
        binding.resumeDiceStrenght.text = "Fuerza: ${player.stenght}"
        binding.resumeDiceGuard.text = "Defensa: ${player.guard}"
        binding.resumeDiceHp.text = "HP ${player.life}/${player.maxLife}"

        binding.resumeDiceBackpack.text = "Mochila con peso ${player.backpack.weight}/${player.backpack.maxWeight}"
        binding.resumeDiceWallet.text = "Tienes ${player.getWalletPurchase()}$"

    }
}