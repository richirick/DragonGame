data class Player(
    private val name: String,
    private var health: Int
) : Creatures() {

    private var healthPointCount = 4
    private var currentHealth = health
    private var inputHealth = health

    private val attack: Int = getRandom() // атака (наносимый урон)
    private val deffese: Int = getRandom() // защита

    init {
        println("Создан игрок c именем ${name}!")
    }

    fun healthPoints() {
        if (healthPointCount > 0 && currentHealth < health) {
            currentHealth += ((currentHealth * 30) / 100)
            healthPointCount--
            println("\u001B[36;1m Вы можете исцелиться еще $healthPointCount раз\u001B[0m")
            if (currentHealth > inputHealth) {
                currentHealth = inputHealth
            } else {
                currentHealth
            }
            println("Здоровье игрока ${getName()} - изначально дано - ${inputHealth} увеличенное - ${currentHealth}%!")
        } else {
            println("Больше нельзя пополнять здоровье!")
            //сообщение выводиться в начале игры, но
            // нужно что бы выводилось допустимое количество исцелений
        }
    }

    fun getName() = name

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