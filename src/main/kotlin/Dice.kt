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
            //как сделать что бы выводилось выпавшее значение на кубике?
        }
    }

    fun getIsSuccess() = isSuccess
}