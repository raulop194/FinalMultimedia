package es.nexcreep.testing.youradventure

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import es.nexcreep.testing.youradventure.database.DatabaseHelper
import es.nexcreep.testing.youradventure.databinding.ActivityResumeBinding
import es.nexcreep.testing.youradventure.model.Player

class ResumeActivity : AppCompatActivity() {
    lateinit var binding: ActivityResumeBinding
    lateinit var profilesHelper: DatabaseHelper

    private lateinit var player: Player

    private var selectedClass: Int = 0
    private var selectedMipmapClass: Int = 0
    private var selectedRace: Int = 0
    private var selectedMipmapRace: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResumeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profilesHelper = DatabaseHelper(applicationContext)

        selectedClass = intent.getIntExtra("CLASS_STRING", R.string.app_name)
        selectedMipmapClass = intent.getIntExtra("CLASS_MIPMAP", R.mipmap.ic_launcher)
        selectedRace = intent.getIntExtra("RACE_STRING", R.string.app_name)
        selectedMipmapRace = intent.getIntExtra("RACE_MIPMAP", R.mipmap.ic_launcher)

        player = Player()

        confImageSequence()
        confDescriptionSequence()


        // Listeners

        binding.buttonStart.setOnClickListener {
            val name = binding.nameField.text.toString()
            player.name = if (name.isEmpty() || name.isBlank()) "Sin nombre" else name
            player.playerRace = getString(selectedRace)
            player.playerClass = getString(selectedClass)

            val password = binding.passwordField.text.toString()
            if (password.isEmpty() || password.isBlank())
                profilesHelper.addPlayerProfile(player)
            else
                profilesHelper.addPlayerProfile(player, password)

            startActivity(
                Intent(this, DiceActivity::class.java)
                    .putExtra("PLAYER_OBJ", Gson().toJson(player))
            )
        }

        binding.buttonRestart.setOnClickListener {
            startActivity(Intent(this, ClassActivity::class.java))
        }

    }

    @SuppressLint("SetTextI18n")
    private fun confDescriptionSequence() {
        binding.textStrenght.text = "${getString(R.string.level_stenght)}: ${player.stenght}"
        binding.textGuard.text = "${getString(R.string.level_guard)}: ${player.guard}"
        binding.textBackpackWeight.text = "${getString(R.string.weight_backpack)}: ${player.backpack.weight}"
        binding.textHp.text = "${getString(R.string.health_points)}: ${player.life}/${player.maxLife}"
        binding.textWalletPurchase.text = "${getString(R.string.waller_purchase)}: ${player.getWalletPurchase()}"
    }

    private fun confImageSequence() {
        binding.textResumeClass.text = resources.getString(selectedClass)
        binding.imageResumeClass.setImageResource(selectedMipmapClass)
        binding.textResumeRace.text = resources.getString(selectedRace)
        binding.imageResumeRace.setImageResource(selectedMipmapRace)
    }
}
