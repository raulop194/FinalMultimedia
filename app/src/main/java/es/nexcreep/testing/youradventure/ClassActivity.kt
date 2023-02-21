package es.nexcreep.testing.youradventure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import es.nexcreep.testing.youradventure.databinding.ActivityMainBinding

class ClassActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private var selectedClass: Int = 0
    private var selectedMipmap: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClass(R.mipmap.mercenario_29, R.string.berserker_text)

        declareButtonClickListener(
            listOf(binding.buttonBerserker, binding.buttonKnight,
                binding.buttonThief, binding.buttonWizard)
        )

        binding.buttonNext.setOnClickListener {
            startActivity(
                Intent(this, RaceActivity::class.java)
                    .putExtra("CLASS_STRING", selectedClass)
                    .putExtra("CLASS_MIPMAP", selectedMipmap)
            )
        }
    }

    private fun declareButtonClickListener(buttons: List<Button>) {
        buttons.forEach {
            when (it) {
                binding.buttonBerserker -> it.setOnClickListener {
                    setClass(R.mipmap.mercenario_29, R.string.berserker_text)
                }
                binding.buttonKnight -> it.setOnClickListener {
                    setClass(R.mipmap.guerrero_29, R.string.knight_text)
                }
                binding.buttonThief -> it.setOnClickListener {
                    setClass(R.mipmap.ladron_29, R.string.thief_text)
                }
                binding.buttonWizard -> it.setOnClickListener {
                    setClass(R.mipmap.hechicero_29, R.string.wizard_text)
                }
            }
        }
    }

    private fun setClass(mipmap: Int, classText: Int) {
        selectedClass = classText
        binding.textClass.text = resources.getString(selectedClass)

        selectedMipmap = mipmap
        binding.imageClass.setImageResource(selectedMipmap)
    }

}