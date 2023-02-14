package es.nexcreep.testing.ejercicio7

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import es.nexcreep.testing.ejercicio7.databinding.ActivityObjectBinding

class ObjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityObjectBinding
    private lateinit var player: Player
    private lateinit var itemProperties: Item
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        player = Gson().fromJson(intent.getStringExtra("PLAYER_OBJ")?:"{}", Player::class.java)
        itemProperties = Item()

        binding.itemName.text = itemProperties.tag
        binding.priceText.text = "Valor: ${itemProperties.price}"
        binding.weightText.text = "Peso: ${itemProperties.weight}"
        binding.healthPointsText.text = "HP ${itemProperties.healthPoints}/${itemProperties.maxHealthPoints}"

        binding.objectActionA.setOnClickListener {
            if (player.backpack.addItem(itemProperties))
                returnToDiceActivity()
            else {
                val toast = Toast.makeText(
                    this,
                    "El objeto no cabe en la mochila.",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
        binding.objectActionB.setOnClickListener {
            returnToDiceActivity()
        }
    }

    private fun returnToDiceActivity() {
        startActivity(Intent(this, DiceActivity::class.java)
            .putExtra("PLAYER_OBJ", Gson().toJson(player)))
    }
}