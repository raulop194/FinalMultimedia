package es.nexcreep.testing.ejercicio7

import kotlin.random.Random

class Personaje {
    var name: String = ""
    var stenght: Int = Random.nextInt(10, 15)
    var guard: Int = Random.nextInt(1, 5)
    var backpack: Int = 100
    var life: Int = 200
    var wallet: HashMap<Int, Int> = mapOf(
        1 to 0, 2 to 0, 5 to 0,
        10 to 0, 20 to 0, 50 to 0,
        100 to 0) as HashMap<Int, Int>


    fun getWalletPurchase(): Int {
        var purchase = 0
        wallet.forEach { (type, count) -> purchase += type * count}
        return purchase
    }
}