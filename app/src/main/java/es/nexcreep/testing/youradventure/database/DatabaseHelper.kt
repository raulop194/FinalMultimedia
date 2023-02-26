package es.nexcreep.testing.youradventure.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.gson.Gson
import es.nexcreep.testing.youradventure.model.Player
import es.nexcreep.testing.youradventure.model.Profile
import es.nexcreep.testing.youradventure.utils.AuthUtils.Companion.sha256
import java.math.BigInteger
import java.security.MessageDigest

@Suppress("UNCHECKED_CAST")
class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 3
        private const val DATABASE = "Profiles.db"
        private const val TABLE_PROFILES = "profile"
        private const val COL_PLAYER_NAME = "player_name"
        private const val COL_PASSWORD = "password"
        private const val COL_PLAYER_JSON = "player_obj"
    }

    /**
     * Gets all profiles from database
     *
     * @return Arraylist with all profile sin database
     * */
    @SuppressLint("Recycle", "Range")
    fun getAllProfiles(): ArrayList<Profile>{
        val profiles = arrayListOf<Profile>()
        val sqlSelect = "SELECT * FROM $TABLE_PROFILES"
        val db = this.readableDatabase
        val cursor = db.rawQuery(sqlSelect, null)
        if (cursor.moveToFirst()) {
            do {
                val playerName = cursor.getString(cursor.getColumnIndex(COL_PLAYER_NAME))
                val password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
                val playerObj = cursor.getString(cursor.getColumnIndex(COL_PLAYER_JSON))
                profiles.add(Profile(playerName, password, playerObj))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return profiles.clone() as ArrayList<Profile>
    }

    /**
     * Add new player profile without a password to the database
     * */
    fun addPlayerProfile(player: Player) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_PLAYER_NAME, player.name)
            put(COL_PLAYER_JSON, Gson().toJson(player))
        }

        db.insert(TABLE_PROFILES, null, values)
        db.close()
    }

    /**
     * Add new player profile with a password encoded in SHA-256 to the database
     * */
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

    /**
     * Removes player from database
     * */
    fun removePlayerProfile(player: Player): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_PROFILES, "$COL_PLAYER_NAME=?", arrayOf(player.name))
        db.close()

        return result
    }

    /**
     * Updates player data from database
     * */
    fun updatePlayerData(player: Player): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_PLAYER_JSON, Gson().toJson(player))
        }
        val result = db.update(TABLE_PROFILES, values,
            "$COL_PLAYER_NAME=?", arrayOf(player.name))
        db.close()

        return result
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sqlCreateTable = "CREATE TABLE $TABLE_PROFILES " +
                "(" +
                "$COL_PLAYER_NAME TEXT PRIMARY KEY, " +
                "$COL_PASSWORD TEXT, " +
                "$COL_PLAYER_JSON TEXT NOT NULL" +
                ")"

        db.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sqlDropTable = "DROP TABLE IF EXISTS $TABLE_PROFILES"

        db.execSQL(sqlDropTable)
        onCreate(db)
    }


}