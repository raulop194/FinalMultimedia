package es.nexcreep.testing.youradventure.model

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import es.nexcreep.testing.youradventure.DiceActivity
import es.nexcreep.testing.youradventure.R
import es.nexcreep.testing.youradventure.database.DatabaseHelper
import es.nexcreep.testing.youradventure.databinding.ItemProfileBinding
import es.nexcreep.testing.youradventure.utils.AuthUtils.Companion.sha256

class AdapterProfile(
    private val profiles: ArrayList<Profile>
): RecyclerView.Adapter<AdapterProfile.ViewHolderProfile>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProfile {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profile, null, false)
        return ViewHolderProfile(view, parent.context)
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    override fun onBindViewHolder(holder: ViewHolderProfile, position: Int) {
        holder.setProfile(profiles[position])
    }

    class ViewHolderProfile(itemView: View,
                            private val parentContext: Context)
        : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemProfileBinding = ItemProfileBinding.bind(itemView)
        private val database: DatabaseHelper = DatabaseHelper(parentContext)

        @SuppressLint("SetTextI18n")
        fun setProfile(profile: Profile) {
            binding.gameName.text = profile.playerName

            val player = Gson().fromJson(profile.playerObj, Player::class.java)
            val stats = parentContext.resources.getString(R.string.game_stats)
                .replace("{0}", player.playerClass)
                .replace("{1}", player.playerRace)
                .replace("{2}", player.stenght.toString())
                .replace("{3}", player.guard.toString())

            Log.d("PROFILE_DATA", profile.toString())

            binding.gameStats.text = stats
            binding.gameStartButton.setOnClickListener {
                if (profile.password != null)
                    openPasswordDialog(profile)
                else {
                    parentContext.startActivity(
                        Intent(parentContext, DiceActivity::class.java)
                            .putExtra("PLAYER_OBJ", profile.playerObj)
                    )
                }
            }
            binding.gameDeleteButton.setOnClickListener {
                if (database.removePlayerProfile(player) != -1) {
                    Toast.makeText(
                        parentContext, "Partida eliminada correctamente", Toast.LENGTH_SHORT
                    ).show()

                    itemView.visibility = View.GONE
                }
            }
        }

        private fun openPasswordDialog(profile: Profile) {
            val builder = AlertDialog.Builder(parentContext)
            val parentActivity = parentContext as Activity
            val view = parentActivity.window.layoutInflater.inflate(
                R.layout.dialog_password, null
            )
            val passwordField = view.findViewById<EditText>(R.id.password_main)

            builder.setView(view)
            builder.setTitle("Requiere contraseña")
            builder.setMessage(
                parentActivity.getString(R.string.dialog_password_subtitle)
            )

            builder.setPositiveButton("Entrar") { _, _ ->
                val password = passwordField.text.toString()
                if (password.sha256() == profile.password) {
                    parentContext.startActivity(
                        Intent(parentContext, DiceActivity::class.java)
                            .putExtra("PLAYER_OBJ", profile.playerObj)
                    )
                }
                else {
                    Toast.makeText(
                        parentContext, "Contraseña Incorrecta", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            builder.create()
                .show()

        }

    }
}