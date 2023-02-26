package es.nexcreep.testing.youradventure

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.gson.Gson
import es.nexcreep.testing.youradventure.database.DatabaseHelper
import es.nexcreep.testing.youradventure.model.Player

class DieActivity : AppCompatActivity() {
    lateinit var database: DatabaseHelper
    lateinit var player: Player
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_die)

        player = Gson().fromJson(
            intent.getStringExtra("PLAYER_OBJ")?:"{}",
            Player::class.java
        )

        database = DatabaseHelper(applicationContext)
        database.removePlayerProfile(player)

        // Listeners
        findViewById<Button>(R.id.restart_btn).setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        }
    }
}