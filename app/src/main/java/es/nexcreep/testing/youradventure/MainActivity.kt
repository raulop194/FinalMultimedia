package es.nexcreep.testing.youradventure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.nexcreep.testing.youradventure.database.DatabaseHelper
import es.nexcreep.testing.youradventure.databinding.ActivityMainBinding
import es.nexcreep.testing.youradventure.model.AdapterProfile

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var database: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = DatabaseHelper(applicationContext)

        binding.newGame.setOnClickListener {
            startActivity(Intent(this, ClassActivity::class.java))
        }

        binding.profilesRecycleView.layoutManager = GridLayoutManager(this, 1)

        val profileAdapter = AdapterProfile(database.getAllProfiles())
        binding.profilesRecycleView.adapter = profileAdapter


    }
}