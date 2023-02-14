package es.nexcreep.testing.ejercicio7

class Enemy(
    val name: String,
    val maxLife: Int,
    val type: EnemyType
) {
    var life: Int = maxLife

    /**
     * Attack's a player
     *
     * @param player player to attack
     * @return Damage caused to player
     * */
    fun attack(player: Player): Int {
        val damage: Int = when (type) {
            EnemyType.BOSS -> 30 / player.guard
            EnemyType.NORMAL -> 20 / player.guard
        }

        if (player.life - damage >= 0) player.life -= damage else player.life = 0
        return damage
    }
}