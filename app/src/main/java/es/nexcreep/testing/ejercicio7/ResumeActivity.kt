package es.nexcreep.testing.ejercicio7

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import es.nexcreep.testing.ejercicio7.databinding.ActivityResumeBinding

class ResumeActivity : AppCompatActivity() {
    lateinit var binding: ActivityResumeBinding
    private lateinit var player: Player

    private var selectedClass: Int = 0
    private var selectedMipmapClass: Int = 0
    private var selectedRace: Int = 0
    private var selectedMipmapRace: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResumeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedClass = intent.getIntExtra("CLASS_STRING", R.string.app_name)
        selectedMipmapClass = intent.getIntExtra("CLASS_MIPMAP", R.mipmap.ic_launcher)
        selectedRace = intent.getIntExtra("RACE_STRING", R.string.app_name)
        selectedMipmapRace = intent.getIntExtra("RACE_MIPMAP", R.mipmap.ic_launcher)

        player = Player()

        confImageSequence()
        confDescriptionSequence()

        // Listeners
        binding.nameField.addTextChangedListener { object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                player.name = binding.nameField.toString()
            }
        } }

        binding.buttonStart.setOnClickListener {
            startActivity(
                Intent(this, DiceActivity::class.java)
                    .putExtra("PLAYER_OBJ", player)
            )
        }

        binding.buttonRestart.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    @SuppressLint("SetTextI18n")
    private fun confDescriptionSequence() {
        binding.textStrenght.text = "${getString(R.string.level_stenght)}: ${player.stenght}"
        binding.textGuard.text = "${getString(R.string.level_guard)}: ${player.guard}"
        binding.textBackpackWeight.text = "${getString(R.string.weight_backpack)}: ${player.backpack}"
        binding.textHp.text = "${getString(R.string.health_points)}: ${player.life}"
        binding.textWalletPurchase.text = "${getString(R.string.waller_purchase)}: ${player.getWalletPurchase()}"
    }

    private fun confImageSequence() {
        binding.textResumeClass.text = resources.getString(selectedClass)
        binding.imageResumeClass.setImageResource(selectedMipmapClass)
        binding.textResumeRace.text = resources.getString(selectedRace)
        binding.imageResumeRace.setImageResource(selectedMipmapRace)
    }
}
