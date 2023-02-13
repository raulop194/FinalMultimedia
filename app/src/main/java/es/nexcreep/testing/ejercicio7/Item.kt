package es.nexcreep.testing.ejercicio7

class Item(
    val tag: String = "Sword",
    val weight: Int = 5,
    val price: Int = 10,
    val healthPoints: Int = 20,
    val maxHealthPoints: Int = 20
) {
    override fun toString(): String {
        return "\"$tag\", Peso $weight, Valor: $price, HP $healthPoints/$maxHealthPoints"
    }
}
