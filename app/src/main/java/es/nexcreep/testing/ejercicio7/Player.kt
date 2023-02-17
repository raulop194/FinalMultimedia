package es.nexcreep.testing.ejercicio7

import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.random.Random

class Player{
    var name: String = "Federico"
    var playerRace: String = ""
    var playerClass: String = ""

    val stenght: Int = Random.nextInt(10, 15)
    val guard: Int = Random.nextInt(1, 5)
    val maxLife: Int = 200

    var life: Int = 200

    var enemiesDefeated = 0

    var backpack: Backpack = Backpack(100)
    var wallet: HashMap<Int, Int> = hashMapOf(
        1 to 0, 2 to 0, 5 to 0,
        10 to 0, 20 to 0, 50 to 0,
        100 to 0)

    fun getWalletPurchase(): Int {
        var purchase = 0
        wallet.forEach { (type, count) -> purchase += type * count}
        return purchase
    }

    fun addCoins(coins: Int) {
        var coinsClone = coins
        val coinType = wallet.keys
        val coinTypeSorted = coinType.sortedDescending()
        coinTypeSorted.forEach {
            while (coinsClone - it >= 0) {
                coinsClone -= it
                wallet[it] = wallet.getOrDefault(it, 0) + 1
            }
        }
    }

    fun removeCoins(coins: Int) {
        val coinDifference = if (getWalletPurchase() - coins >= 0) getWalletPurchase() - coins else 0

        wallet = hashMapOf(
            1 to 0, 2 to 0, 5 to 0,
            10 to 0, 20 to 0, 50 to 0,
            100 to 0)

        addCoins(coinDifference)
    }

    /**
     * Attack's an enemy
     *
     * @param enemy Enemy to attack
     * @return Damage caused to enemy
     * */
    fun attack(enemy: Enemy): Int {
        if (enemy.life - stenght >= 0) enemy.life -= stenght else enemy.life = 0
        return stenght
    }
}