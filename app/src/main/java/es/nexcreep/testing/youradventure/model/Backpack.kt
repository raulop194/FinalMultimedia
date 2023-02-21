package es.nexcreep.testing.youradventure.model


class Backpack (var weight: Int = 100, val maxWeight: Int = 100){
    var items = arrayListOf<Item>()

    fun addItem(item: Item): Boolean {
        val difference = weight - item.weight
        if (difference > 0){
            items.add(item)
            weight = difference
            return true
        }
        return false
    }

    fun removeItemAt(index: Int): Item? {
        if (items.isEmpty()) return null
        val item = items.removeAt(index)
        weight += item.weight
        return item
    }

    override fun toString(): String {
        return "{Peso = $weight/$maxWeight, Items = $items}"
    }
}