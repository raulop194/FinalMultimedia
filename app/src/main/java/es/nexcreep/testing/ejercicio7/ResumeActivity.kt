package es.nexcreep.testing.ejercicio7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.nexcreep.testing.ejercicio7.databinding.ActivityResumeBinding

class ResumeActivity : AppCompatActivity() {
    lateinit var binding: ActivityResumeBinding

    private var selectedClass: Int = 0
    private var selectedMipmap: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResumeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedClass = intent.getIntExtra("CLASS_STRING", R.string.app_name)
        selectedMipmap = intent.getIntExtra("CLASS_MIPMAP", R.mipmap.ic_launcher)


        binding.textView.text = resources.getString(selectedClass)
        binding.imageView.setImageResource(selectedMipmap)

    }
}