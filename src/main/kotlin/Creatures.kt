import kotlin.random.Random

abstract class Creatures {

    abstract fun getAttack(): Int
    abstract fun getDeffese(): Int

    abstract fun setHealthPoints(damage: Int)
    abstract fun isDied(): Boolean
    fun getRandom() = Random.nextInt(30 - 1) + 1

}

// Игрок
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
            println("Вы можете исцелиться еще $healthPointCount раз")
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

//Монстер
data class Monster(
    private var health: Int
) : Creatures() {

    var currentHealth = health

    private val attack: Int = getRandom() // атака (наносимый урон)
    private val deffese: Int = getRandom() // защита

    init {
        println("Монстр создан ${this.hashCode()}!")
    }

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

class Dice {
    private var countCube: IntRange = 1..6
    private var isSuccess = false

    private var mAttackN: Int = 1

    fun calculateMAttackN(attackAttack: Int, secure: Int) {
        mAttackN = attackAttack - secure + 1
    }

    fun roll() {
        val numBuffer = mutableSetOf<Int>()
        if (mAttackN >= 1) {
            for (elem in 0..mAttackN) {
                numBuffer.add(countCube.random())
           break }
            isSuccess = numBuffer.firstOrNull { item -> item == 5 || item == 6 } != null
            println("Результат броска ${isSuccess}")
        }
    }

    fun getIsSuccess() = isSuccess
}

class Game(val player: Player, val monster: Monster) {
    private val dice: Dice = Dice()
    private var isFinishGame = false

    fun createGame() {
        println("Игрок ${player.getName()} наносит удар первый!")
        while (!isFinishGame) {
            playersMove()
            showStatistic()
            if (checkFinishGame()) return
            monsterMove()
            showStatistic()
            if (checkFinishGame()) return
            showMenu()
        }
    }

    private fun playersMove() {
        if (dice.getIsSuccess()) {
            damageMonster()
        } else {
            println("Защита монстра не пробита!")
        }
    }

    private fun monsterMove() {
        dice.calculateMAttackN(monster.getAttack(), player.getDeffese())
        dice.roll()
        if (dice.getIsSuccess()) {
            damagePlayer()
        } else {
            println("Защита игрока не пробита!")
        }
    }

    private fun damagePlayer() {
        val damage = Random.nextInt(monster.getAttack() - 1) + 1
        println("Игрок получает урон $damage")
        player.setHealthPoints(damage)
    }

    private fun damageMonster() {
        val damage = Random.nextInt(player.getAttack() - 1) + 1
        println("Урон монстру равен $damage")
        monster.setHealthPoints(damage)
    }

    private fun showMenu() {
        println("Бросить кубик?")
        println("1 - Да")
        println("2 - Пополнить здоровье")
        println("0 - Выход из игры")
        val commandNumber = readlnOrNull()?.toInt() ?: 0

        when (commandNumber) {
            1 -> {
                dice.calculateMAttackN(player.getAttack(), monster.getDeffese())
                dice.roll()
            }

            2 -> {
                player.healthPoints()
                showMenu()
            } // пополнить здоровье
            0 -> {
                isFinishGame = true
            }// выход
        }
    }

    private fun checkFinishGame(): Boolean {
        if (monster.isDied()) {
            showStatistic()
            println("Ура вы победили!")
            return true
        }
        if (player.isDied()) {
            showStatistic()
            println("Вы проиграли!")
            return true
        }
        return false
    }

    private fun showStatistic() {
        println("Здоровье игрока ${player.getName()} = ${player.getHealth()} %")
        println("Здоровье монстра = ${monster.getHealth()} %")
    }
}
