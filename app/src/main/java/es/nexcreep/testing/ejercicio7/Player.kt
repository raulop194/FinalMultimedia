package es.nexcreep.testing.ejercicio7

import android.os.Parcel
import android.os.Parcelable
import kotlin.random.Random

class Player() : Parcelable {
    var name: String = ""
    var stenght: Int = Random.nextInt(10, 15)
    var guard: Int = Random.nextInt(1, 5)
    var backpack: Int = 100
    var life: Int = 200
    var wallet: HashMap<Int, Int> = mapOf(
        1 to 0, 2 to 0, 5 to 0,
        10 to 0, 20 to 0, 50 to 0,
        100 to 0) as HashMap<Int, Int>

    constructor(parcel: Parcel) : this() {
        name = parcel.readString().toString()
        stenght = parcel.readInt()
        guard = parcel.readInt()
        backpack = parcel.readInt()
        life = parcel.readInt()
    }


    fun getWalletPurchase(): Int {
        var purchase = 0
        wallet.forEach { (type, count) -> purchase += type * count}
        return purchase
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(stenght)
        parcel.writeInt(guard)
        parcel.writeInt(backpack)
        parcel.writeInt(life)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player {
            return Player(parcel)
        }

        override fun newArray(size: Int): Array<Player?> {
            return arrayOfNulls(size)
        }
    }
}