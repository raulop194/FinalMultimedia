package es.nexcreep.testing.youradventure.model

data class Item(
    val tag: String = "Sword",
    val weight: Int = 5,
    val price: Int = 10,
    val healthPoints: Int = 20,
    val maxHealthPoints: Int = 20
)
