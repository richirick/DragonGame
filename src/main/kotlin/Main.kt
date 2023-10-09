fun main() {
    startGame()
}

fun startGame() {
    println("Добро пожаловать в Dragon Game!")
    println("Начало игры !")


    print("Введите имя игрока: ")
    val name = readlnOrNull() ?: "NO Name"
    print("Введите здровье игрока: ")
    val health = readlnOrNull()?.toIntOrNull() ?: 100

    val player = Player(name, health)

    println("Здоровье игрока ${player.getName()} - ${health}%!")
    println("${player.getAttack()} - урон игрока!")
    println("${player.getDeffese()} - защита игрока!")
    println("=========================================")

    print("Введите здровье монстра: ")
    val healthMonster = readlnOrNull()?.toIntOrNull() ?: 100

    val monster = Monster(name = "Смауг", healthMonster)

    println("Монстр создан!")
    println("Имя монстра - ${monster.name}")
    println("Здоровье монстра ${monster.name} - ${healthMonster}%!")
    println("${monster.getAttack()} - урон монстра!")
    println("${monster.getDeffese()} - защита монстра!")
    println("=========================================")

    println("Начать игру?")
    println("1 - Да")
    println("0 - Выход из игры")

    val commandNumber = readlnOrNull()?.toInt() ?: 0

    when (commandNumber) {
        1 -> {
            playGame(player, monster)
        }
        0 -> {
            println("Игра окончена!")
            return
        }
    }
    showMenu()
}

fun playGame(player: Player, monster: Monster) {
    val game = Game(player, monster)
    game.createGame()
}

private fun showMenu() {
    println("Попробовать еще раз?")
    println("1 - Да")
    println("0 - Выход из игры")
    val commandNumber = readlnOrNull()?.toInt() ?: 0

    when (commandNumber) {
        1 -> {
            startGame()
        }

        0 -> {
            return
        }
    }
}
