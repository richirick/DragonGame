data class Monster(
    var name: String = "Смауг",
    private var health: Int
) : Creatures() {

    var currentHealth = health

    private val attack: Int = getRandom()
    private val deffese: Int = getRandom()


    fun getHealth() = currentHealth

    override fun getAttack(): Int {
        return attack
    }

    override fun getDeffese(): Int {
        return deffese
    }

    override fun setHealthPoints(damage: Int) {
        if (currentHealth > damage) {
            currentHealth -= damage
        } else {
            currentHealth = 0
        }
    }

    override fun isDied(): Boolean {
        return currentHealth == 0
    }
}