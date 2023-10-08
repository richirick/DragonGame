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
        println("Игрок создан c именем ${name}!")
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
            }
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
        println("Урон игроку равен $damage")
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

//import kotlin.random.Random
//
//abstract class Creatures(
//    private val healthPoints: Int, // здоровье
//) {
//
//
//    private val attack: Int = getRandom() // атака (наносимый урон)
//    private val deffese: Int = getRandom() // защита
//
//
//    fun getAttack(): Int = attack
//    fun getDeffese(): Int = deffese
//    fun getHealthPoints(): Int = healthPoints
//    abstract fun setHealthPoints(damage: Int)
//
//    private fun getRandom() = Random.nextInt(30 - 1) + 1
//
//}
//
//// Игрок
//data class Player(
//    private val name: String,
//    private var health: Int
//) : Creatures(health) {
//
//    init {
//        println("Игрок создан c именем ${name}!")
//    }
//
//    override fun setHealthPoints(damage: Int) {
//        if (health > damage) {
//            health -= damage
//        } else {
//            health = 0
//            //call function return health
//        }
//    }
//}
//
////Монстер
//data class Monster(
//    private val name: String = "Смауг",
//    private var health: Int
//) : Creatures(health) {
//
//
//    override fun setHealthPoints(damage: Int) {
//        if (health > damage) {
//            health -= damage
//        } else {
//            health = 0
//            //finish game and congratulation user
//        }
//    }
//
//    init {
//        println("Монстр создан ${this.hashCode()}!")
//    }
//}
//
//class Dice {
//    private var countCube: IntRange = 1..6
//    private var isSuccess = false
//
//    private var mAttackN: Int  = 1
//
//
//
//    init {
//        println("Бросить кубик дальше?")
//        println("1 - Да")
//        println("2 - Пополнить здоровье")
//        println("0 - Выход из игры")
//        val commandNumber = readlnOrNull()?.toInt() ?: 0
//
//        when (commandNumber) {
//            1 -> roll()
//            2 -> healthPoints() // предложить
//            0 -> exit() // выход
//        }
//    }
//
//    fun calculateMAttackN(attackAttack: Int, secure: Int) {
//        mAttackN = attackAttack - secure + 1
//    }
//
//    private fun roll() {
//        val numBuffer = mutableSetOf<Int>()
//        if (mAttackN >= 1) {
//            for (elem in 0..mAttackN) {
//                numBuffer.add(countCube.random())
//            }
//            isSuccess = numBuffer.firstOrNull { item -> item == 5 || item == 6 } != null
//            println("Результат броска ${isSuccess}")
//        }
//    }
//
//    fun getIsSuccess() = isSuccess
//
//    fun healthPoints() {
//        ///
//    }
//
//    private fun exit() {
//        isFinishGame = true
//    }
//}
//
////import kotlin.random.Random
////import kotlin.system.exitProcess
////
////abstract class Creatures(
////    private var healthPoints: Int, // здоровье
////) {
////
////
////    private val attack: Int = getRandom() // атака (наносимый урон)
////    private val defense: Int = getRandom() // защита
////
////
////    fun getAttack(): Int = attack
////    fun getDeffese(): Int = defense
////    open fun getHealthPoints(): Int = healthPoints
////   abstract fun setHealthPoints(damage:Int)
////   private fun getRandom() = Random.nextInt(30-1)+1
//////   {
//////        if (healthPoints>damage){
//////        healthPoints = healthPoints- damage
//////        } else {
//////            healthPoints = 0
//////            // call function return health
//////        }
//////    }
////
////    private fun getRandom() = Random.nextInt(30 - 1) + 1
////
////}
////
////// Игрок
////data class Player(val name : String, val health: Int) :
////    Creatures(health) {
////
////    init {
////        println("Игрок создан c именем ${name}!")
////    }
//////    override fun getHealthPoints(health: Double) {
////////        var health = healthPoints
////////   var health = readln().toDouble()
//////        var counter = 4
//////        repeat(counter) {
//////            while ( counter !=0 && health <100 && health > 0 ) {
//////                println("Бросить кубик или лечиться?(введите бросок или лечиться)")
//////                val answer : String = readln().lowercase().uppercase()
//////                val answer1 = ""
//////                if (answer == "лечиться")
////////            (health / 100) * healthPercent
//////                    health + ((health / 100) * 30)
//////                println("Теперь ваше здоровье  ${health + ((health / 100) * 30)}")
//////                counter--
//////
//////                println("Вы можете исцелиться еще $counter раз")
//////            }
//////        }
//////
//////    }
////
////}
////
//////Монстер
////data class Monster(private var health: Int) :
////    Creatures(health) {
////    override fun setHealthPoints(damage: Int) {
////        if ()
////    }
////
////    init {
////        println("Монстр создан ${this.hashCode()}!")
////    }
////
////}
////
////fun getHealthPoints(health: Double) {
////    val dice = Dice()
////    var counter = 4
////    repeat(counter) {
////        while (counter != 0 && health < 100 && health > 0) {
////            println("Бросить кубик или лечиться?(введите бросок или лечиться)")
////            val answer: String = readln().lowercase().uppercase()
////            val answer1 = "лечиться"
////            var answer2 = "Бросок"
////            //            (health / 100) * healthPercent
////            if (answer in answer1)
////                health + ((health / 100) * 30)
////            println("Теперь ваше здоровье  ${health + ((health / 100) * 30)}")
////            counter--
////            println("Вы можете исцелиться еще $counter раз")
////
////            if (answer in answer2){
////                dice.roll()
////            }
////        }
////
////    }
////}
////
////fun main(){
////    println("Введите ваше здоровье")
////    getHealthPoints(health = readln().toDouble())
////    println( "Бросить кубик?")
////    val dice =Dice()
////    dice.roll()
////}
////data class Dice(private val mAttackN: Int) {
////    val answers = readln().toString()
////    var t: IntRange = 1..6
////    fun roll(){
////val numBuffer = mutableSetOf<Int>()
////    }
////    fun roll1() {
////        when(answers){
////            "Да" -> {
////                    val aM = t.random()
////                val am2 = aM*15
////                    println("Выпало ${aM}, урон ${am2}")}
//////            if (aM == 5..6){
//////                println()
//////            }
//////создаем мидификатор атакаки, атака зависит от числа выпавшего на кубике
//////если на кубике выпало больше 4 (5 или 6) то атака считается успешной и наноситься пропорцианально больше урона(урон умножется на 15 и 2)
////            "Нет" ->{
////                println("Бросить еще кубик?")
////            }
////        }
////    }
////}
//////        if (answer1 in answers) {
//////           println("Выпало ${t.random()}")
//////        } else
//////        if (answer2 in answers) {
//////            println("Бросить еще кубик или лечиться?")
//////        }
////
//////fun attackCalculate(player.getAttack(), monster.getDeffese()+1) {
////////    val dice = Dice()
////////    dice.t
//////    val attack = player.getAttack() - (monster.getDeffese()+1)
//////
//////    }
////// модификатор атаки
////    // делаем бросок кубиком (создать класс кубик и описать в нем правильно функционал)
//////}
////
////
//////fun healthRecovery(health: Double) {
////////   var health = readln().toDouble()
//////    var counter = 4
//////    repeat(counter) { while ( counter !=0 && health <100 && health >0 ) {
//////
//////           println("Бросить кубик или лечиться?(введите бросок или лечиться)")
//////           val answer = readln().toString().lowercase().uppercase()
//////           if (answer in "Лечиться")
////////            (health / 100) * healthPercent
//////               health + ((health / 100) * 30)
//////           println("Теперь ваше здоровье  ${health + ((health / 100) * 30)}")
//////        counter--
//////           println("Вы можете исцелиться еще $counter раз")
//////
//////       }
//////
//////    }
//////}