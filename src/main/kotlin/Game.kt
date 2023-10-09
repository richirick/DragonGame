import kotlin.random.Random

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
//        dice.calculateMAttackN(player.getAttack(), monster.getDeffese())
//        dice.roll()
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
        println("\u001b[31;1m Игрок получает урон $damage\u001b[0m")
        player.setHealthPoints(damage)
    }

    private fun damageMonster() {
        val damage = Random.nextInt(player.getAttack() - 1) + 1
        println("\u001b[33;1m Урон монстру равен $damage\u001b[0m")
        monster.setHealthPoints(damage)
    }

    private fun showMenu() {
        println("=========================================")
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
            println("=========================================")
            println("\u001B[35;1m Ура вы победили!\u001B[0m")
            println("=========================================")
            return true
        }
        if (player.isDied()) {
            showStatistic()
            println("==========================================================================")
            println("\u001B[31;1m Вы проиграли!\u001B[0m")
            println("\u001B[31;1m Вы побеждены, убиты и съедены! Слышите? Вы мертвее мертвого.\u001B[0m")
            println("==========================================================================")
            return true
        }
        return false
    }

    private fun showStatistic() {
        println("Здоровье игрока ${player.getName()} = ${player.getHealth()} %")
        println("Здоровье монстра = ${monster.getHealth()} %")
    }
}