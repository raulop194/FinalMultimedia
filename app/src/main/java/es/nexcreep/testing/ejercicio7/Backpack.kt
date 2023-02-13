package es.nexcreep.testing.ejercicio7


class Backpack (var weight: Int = 100, var maxWeight: Int = 100){
    var items = arrayListOf<Item>()

    fun addItem(item: Item) {
        val difference = weight - item.weight
        if (difference > 0){
            items.add(item)
            weight = difference
        }
    }

    override fun toString(): String {
        return "{Peso = $weight/$maxWeight, Items = $items}"
    }
}