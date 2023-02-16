package es.nexcreep.testing.ejercicio7

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import es.nexcreep.testing.ejercicio7.databinding.ActivityTraderBinding

class TraderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTraderBinding
    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTraderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        player = Gson().fromJson(intent.getStringExtra("PLAYER_OBJ")?:"{}", Player::class.java)

        val groupA = listOf(binding.traderActionA, binding.traderActionB)
        val groupB = listOf(binding.tradeBuy, binding.tradeSell, binding.tradeCancel)


        binding.traderActionA.setOnClickListener {
            groupA.forEach { it.isEnabled = false }
            groupB.forEach { it.visibility = View.VISIBLE }
        }
        binding.traderActionB.setOnClickListener {
            startActivity(
                Intent(this, DiceActivity::class.java)
                    .putExtra("PLAYER_OBJ", Gson().toJson(player))
            )
        }

        binding.tradeCancel.setOnClickListener {
            groupA.forEach { it.isEnabled = true }
            groupB.forEach { it.visibility = View.INVISIBLE }
            binding.traderImage.setImageResource(R.mipmap.grierat)
        }

        binding.tradeSell.setOnClickListener {
            openSellDialog()
        }

        binding.tradeBuy.setOnClickListener {
            openBuyDialog()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun openSellDialog() {
        binding.traderImage.setImageResource(R.mipmap.mochila)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this@TraderActivity)
        val view = layoutInflater.inflate(R.layout.dialog_traider, null)
        val seekBar = view.findViewById<SeekBar>(R.id.seekBar3)

        builder.setView(view)
        builder.setTitle("Vender")
        builder.setMessage("Selecciona la cantidad de items que quieres vender.")

        seekBar.max = player.backpack.items.size
        view.findViewById<TextView>(R.id.value).text = "Cantidad a vender: ${seekBar.progress}"
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                view.findViewById<TextView>(R.id.value).text = "Cantidad a vender: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { }

            override fun onStopTrackingTouch(seekBar: SeekBar?) { }

            }
        )

        builder.setPositiveButton("Vender") { _, _ ->
            var totalPrice = 0
            val itemCount = seekBar.progress
            repeat(itemCount) {
                val removedItem: Item? = player.backpack.removeItemAt(0)
                if (removedItem != null)
                    totalPrice += removedItem.price
                Log.d("Dialog", "${player.backpack.items}")
            }
            player.addCoins(totalPrice)

            val toast = Toast.makeText(
                this@TraderActivity,
                "Se han vendido los ${seekBar.progress} primeros objetos de la mochila.",
                Toast.LENGTH_LONG
            )
            toast.show()

            binding.traderImage.setImageResource(R.mipmap.grierat)
        }
        val dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun openBuyDialog() {
        binding.traderImage.setImageResource(R.mipmap.titanita_centelleante)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this@TraderActivity)
        val view = layoutInflater.inflate(R.layout.dialog_traider, null)
        val seekBar = view.findViewById<SeekBar>(R.id.seekBar3)
        val itemOnSell = Item(tag = "Titanita Centellante", weight = 2, price = 125)

        builder.setView(view)
        builder.setTitle("Comprar")
        builder.setMessage("Selecciona la cantidad de items que quieres comprar." +
                "\n${itemOnSell.tag} \n\t-> Precio = ${itemOnSell.price}\n\t-> Peso = ${itemOnSell.weight}")

        val purchaseLimit = if(player.getWalletPurchase() <= 0) 0 else player.getWalletPurchase()/ itemOnSell.price
        val weightLimit = (player.backpack.maxWeight - (player.backpack.maxWeight - player.backpack.weight)) / itemOnSell.weight
        seekBar.max = if (purchaseLimit < weightLimit) purchaseLimit else weightLimit

        view.findViewById<TextView>(R.id.value).text = "Cantidad a comprar: ${seekBar.progress}"
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                view.findViewById<TextView>(R.id.value).text = "Cantidad a comprar: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { }

            override fun onStopTrackingTouch(seekBar: SeekBar?) { }

        }
        )

        builder.setPositiveButton("Comprar") { _, _ ->
            repeat(seekBar.progress) {
                player.removeCoins(itemOnSell.price)
                player.backpack.addItem(itemOnSell)
            }

            val toast = Toast.makeText(
                this@TraderActivity,
                "Se han comprado ${seekBar.progress} de ${itemOnSell.tag}",
                Toast.LENGTH_LONG
            )
            toast.show()

            binding.traderImage.setImageResource(R.mipmap.grierat)
        }
        val dialog = builder.create()
        dialog.show()
    }
}