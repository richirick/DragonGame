import kotlin.random.Random

abstract class Creatures {

    abstract fun getAttack(): Int
    abstract fun getDeffese(): Int

    abstract fun setHealthPoints(damage: Int)
    abstract fun isDied(): Boolean
    fun getRandom() = Random.nextInt(30 - 1) + 1

}
