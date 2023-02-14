package es.nexcreep.testing.ejercicio7

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.gson.Gson
import es.nexcreep.testing.ejercicio7.databinding.ActivityEnemyBinding
import kotlin.math.roundToInt
import kotlin.random.Random

class EnemyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnemyBinding
    private lateinit var player: Player
    private lateinit var enemy: Enemy

    private var round = 1

    private lateinit var playerActions: List<Button>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnemyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        player = Gson().fromJson(intent.getStringExtra("PLAYER_OBJ")?:"{}", Player::class.java)
        enemy = spawnEnemy(getPercentage())

        binding.enemysName.text = enemy.name

        when (enemy.type) {
            EnemyType.BOSS -> binding.enemyImage.setImageResource(R.mipmap.sabio_de_cristal)
            EnemyType.NORMAL -> binding.enemyImage.setImageResource(R.mipmap.hombre_cuervo)
        }

        binding.hpEnemy.progress = enemy.maxLife
        binding.hpEnemy.max = enemy.maxLife

        binding.hpPlayer.progress = player.maxLife
        binding.hpPlayer.max = player.maxLife

        binding.turnMsg.text = "Ronda $round"

        playerActions = listOf(binding.actionA, binding.actionB, binding.actionC)

        playerActions.forEach {
            it.setOnClickListener { b -> playerMove(b.id)}
        }

    }

    private fun getPercentage(): Float {
        return Random.nextInt(1, 7) / 6f * 100f
    }

    private fun spawnEnemy(percentage: Float): Enemy {
        return when {
            percentage.roundToInt() <= 80 -> Enemy("Hombre Cuervo", 100, EnemyType.NORMAL)
            percentage.roundToInt() <= 100 -> Enemy("Sabio de Cristal", 200, EnemyType.BOSS)
            else -> Enemy("BUG!!!", 1000, EnemyType.BOSS)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun playerAttack() {
        val damage = player.attack(enemy)
        binding.damageTo.text = "Has hecho $damage al enemigo."
        binding.hpEnemy.progress -= damage
    }

    @SuppressLint("SetTextI18n")
    private fun playerScape() {
        when (Random.nextInt(1, 7)) {
            in 5..6 -> nextActivity(DiceActivity::class.java)
            in 1..4 -> binding.damageTo.text = "No has podido escapar..."
        }

    }

    @SuppressLint("SetTextI18n")
    private fun playerHeal() {
        if (player.life < player.maxLife && player.backpack.weight <= player.backpack.maxWeight) {
            val inc = if (player.maxLife - player.life < 20) player.maxLife - player.life else 20
            player.life += inc
            player.backpack.removeItemAt(0)

            binding.damageTo.text = "Has recobrado $inc puntos de salud."
            binding.hpPlayer.progress += inc
        } else {
            binding.damageTo.text = "No tienes objetos o tienes la vida al maximo para curarte."
        }
    }

    @SuppressLint("SetTextI18n")
    private fun playerMove(actionId: Int) {

        when (actionId) {
            R.id.action_a -> playerAttack()
            R.id.action_b -> playerScape()
            R.id.action_c -> playerHeal()
        }

        playerActions.forEach { it.isEnabled = false }
        checkBattle()

        enemyMove()
    }

    @SuppressLint("SetTextI18n")
    private fun enemyMove() {
        val damage = enemy.attack(player)
        binding.damageOf.text = "El enemigo te ha hecho $damage de daño."
        binding.hpPlayer.progress -= damage

        checkBattle()
        round +=1
        binding.turnMsg.text = "Ronda $round"
        playerActions.forEach { it.isEnabled = true }
    }

    private fun checkBattle() {
        if (player.life <= 0)
            nextActivity(DiceActivity::class.java)

        if (enemy.life <= 0) {
            Toast.makeText(
                this@EnemyActivity,
                "Has derrotado al enemigo, ¡enhorabuena!",
                Toast.LENGTH_LONG
            ).show()

            listOf(
                Item("Estus (Curación)", 1, 1),
                Item("Estus (Curación)", 1, 1),
                Item("Estus (Curación)", 1, 1)
            ).forEach {
                player.backpack.addItem(it)
            }
            player.addCoins(100)

            nextActivity(DiceActivity::class.java)
        }
    }

    private fun nextActivity(activity: Class<*>) {
        startActivity(
            Intent(this, activity)
                .putExtra("PLAYER_OBJ", Gson().toJson(player))
        )
    }
}