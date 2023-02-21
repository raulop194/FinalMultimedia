package es.nexcreep.testing.youradventure.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.gson.Gson
import es.nexcreep.testing.youradventure.model.Player
import es.nexcreep.testing.youradventure.model.Profile
import java.math.BigInteger
import java.security.MessageDigest

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE = "Profiles.db"
        private const val TABLE_PROFILES = "profile"
        private const val KEY_ID = "_id"
        private const val COL_PLAYER_NAME = "player_name"
        private const val COL_PASSWORD = "password"
        private const val COL_PLAYER_JSON = "player_obj"
    }

    private fun String.sha256(): String {
        val md = MessageDigest.getInstance("SHA-256")
        return BigInteger(1, md.digest(toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }

    @SuppressLint("Recycle", "Range")
    fun getAllProfiles(): ArrayList<Profile>{
        val profiles = arrayListOf<Profile>()
        val sqlSelect = "SELECT * FROM $TABLE_PROFILES"
        val db = this.readableDatabase
        val cursor = db.rawQuery(sqlSelect, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val playerName = cursor.getString(cursor.getColumnIndex(COL_PLAYER_NAME))
                val password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
                val playerObj = cursor.getString(cursor.getColumnIndex(COL_PLAYER_JSON))
                profiles.add(Profile(id, playerName, password, playerObj))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return profiles.clone() as ArrayList<Profile>
    }

    fun addPlayerProfile(player: Player) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_PLAYER_NAME, player.name)
            put(COL_PLAYER_JSON, Gson().toJson(player))
        }

        db.insert(TABLE_PROFILES, null, values)
        db.close()
    }

    fun addPlayerProfile(player: Player, password: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_PLAYER_NAME, player.name)
            put(COL_PASSWORD, password.sha256())
            put(COL_PLAYER_JSON, Gson().toJson(player))
        }

        db.insert(TABLE_PROFILES, null, values)
        db.close()
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sqlCreateTable = "CREATE TABLE $TABLE_PROFILES " +
                "($KEY_ID INTEGER PRIMARY KEY, " +
                "$COL_PLAYER_NAME TEXT, " +
                "$COL_PASSWORD TEXT, " +
                "$COL_PLAYER_JSON TEXT)"

        db.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sqlDropTable = "DROP TABLE IF EXISTS $TABLE_PROFILES"

        db.execSQL(sqlDropTable)
    }


}