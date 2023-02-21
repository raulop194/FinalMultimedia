package es.nexcreep.testing.youradventure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import es.nexcreep.testing.youradventure.databinding.ActivityRaceBinding

class RaceActivity : AppCompatActivity() {
    private var selectedMipmap: Int = 0
    private var selectedRace: Int = 0
    lateinit var binding: ActivityRaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRaceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setClass(R.mipmap.human, R.string.human_text)

        declareButtonClickListener(
            listOf(binding.buttonHuman, binding.buttonElf,
                binding.buttonGoblin, binding.buttonDwarf)
        )

        binding.buttonFinnish.setOnClickListener {
            startActivity(
                Intent(this, ResumeActivity::class.java)
                    .putExtra("RACE_STRING", selectedRace)
                    .putExtra("RACE_MIPMAP", selectedMipmap)
                    .putExtra("CLASS_STRING", intent.getIntExtra("CLASS_STRING", 0))
                    .putExtra("CLASS_MIPMAP", intent.getIntExtra("CLASS_MIPMAP", 0))
            )
        }

    }

    private fun declareButtonClickListener(buttons: List<Button>) {
        buttons.forEach {
            when (it) {
                binding.buttonHuman -> it.setOnClickListener {
                    setClass(R.mipmap.human, R.string.human_text)
                }
                binding.buttonElf -> it.setOnClickListener {
                    setClass(R.mipmap.elf, R.string.elf_text)
                }
                binding.buttonGoblin -> it.setOnClickListener {
                    setClass(R.mipmap.goblin, R.string.goblin_text)
                }
                binding.buttonDwarf -> it.setOnClickListener {
                    setClass(R.mipmap.dwraft, R.string.dwarf_text)
                }
            }
        }
    }

    private fun setClass(mipmap: Int, classText: Int) {
        selectedRace = classText
        binding.textRace.text = resources.getString(selectedRace)

        selectedMipmap = mipmap
        binding.imageRace.setImageResource(selectedMipmap)
    }
}