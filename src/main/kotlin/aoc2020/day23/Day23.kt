package aoc2020.day23

import com.ginsberg.cirkle.circular

fun playGame(input: List<Int>, moves: Int = 100): String {
    var list = input.toMutableList().circular()
    var currentCup = input[0]
    for (move in (1..moves)) {
        val currentCupIndex = list.indexOf(currentCup)
        list = rotateListToHaveCurrentAtTheStart(currentCupIndex, list)

        val listCopy = list.toMutableList()
        val pickUp = listOf(list.removeAt(1), list.removeAt(1), list.removeAt(1))
        val min = list.minOrNull()
        val listMinusLargerThanCurrent = list.toMutableList()
        list
            .filter { it >= currentCup }
            .forEach { listMinusLargerThanCurrent.remove(it) }
        val nextCup = list[list.indexOf(currentCup) + 1]
        val max = listMinusLargerThanCurrent.maxOrNull()
        val indexToDropPickups = if (currentCup == min) {
            listCopy.indexOf(list.maxOrNull())
        } else {
            listCopy.indexOf(max)
        }
        for (pu in pickUp) {
            listCopy.remove(pu)
            listCopy.add(indexToDropPickups, pu)
        }
        list = listCopy.circular()
        currentCup = nextCup
    }
    return listAfter1ToString(list)
}

fun playGameV2(input: List<Int>, moves: Long = 100): String {
    val list = input.toMutableList()
    var currentCup = input[0]
    val maxCup = input.size

    for (m in (1..moves)) {
        val pickUp = listOf(list.removeAt(1), list.removeAt(1), list.removeAt(1))
        val nextCup = list[list.indexOf(currentCup) + 1]
        var candidate = currentCup - 1
        while (!list.contains(candidate)) {
            if (candidate <= 0) {
                candidate = maxCup
            } else {
                candidate -= 1
            }
        }
        val indexToDropPickups = list.indexOf(candidate) + 1

        for (pu in pickUp.reversed()) {
            list.add(indexToDropPickups, pu)
        }
        list.remove(currentCup)
        list.add(currentCup)
        currentCup = nextCup
    }
    return listAfter1ToString(list)
}

fun playGamePart2(input: List<Int>, moves: Long = 10000000): Long {
    val list = addCupsToList(input)
    val cupsList = generateLinkedList(list)
    val valueToCupMap = mutableMapOf<Int, Cup>()
    for (cup in cupsList) {
        valueToCupMap[cup.value] = cup
    }
    var currentCup = cupsList[0]
    val minCup = 1
    val maxCup = list.size

    for (m in (1..moves)) {
        val pick1 = currentCup.right!!
        val pick2 = pick1.right!!
        val pick3 = pick2.right!!
        val pickupValues = listOf(pick1.value, pick2.value, pick3.value)
        currentCup.right = pick3.right
        currentCup.right!!.left = currentCup

        var dropValue = currentCup.value - 1
        while (dropValue in pickupValues || dropValue > maxCup || dropValue < minCup) {
            dropValue -= 1
            if (dropValue < minCup) {
                dropValue = maxCup
            }
        }
        val dropCup = valueToCupMap[dropValue]
        pick3.right = dropCup!!.right
        pick3.right!!.left = pick3
        dropCup.right = pick1
        pick1.left = dropCup
        currentCup = currentCup.right!!
    }

    val cupOnRightOfCupOne = valueToCupMap[1]!!.right
    return cupOnRightOfCupOne!!.value.toLong() * cupOnRightOfCupOne.right!!.value
}

private fun generateLinkedList(list: List<Int>): MutableList<Cup> {
    val cupsList = mutableListOf<Cup>()
    cupsList.add(Cup(list[0]))
    for (i in 0 until list.size - 1) {
        cupsList.add(Cup(list[i + 1]))
        cupsList[i].right = cupsList[i + 1]
        cupsList[i + 1].left = cupsList[i]
    }
    cupsList[0].left = cupsList.last()
    cupsList.last().right = cupsList[0]
    return cupsList
}

private fun rotateListToHaveCurrentAtTheStart(index: Int, list: List<Int>): MutableList<Int> {
    val listWithCurrentAtStart = mutableListOf<Int>().circular()
    (index until list.size + index).mapTo(listWithCurrentAtStart) { list[it] }
    return listWithCurrentAtStart
}

private fun listAfter1ToString(list: List<Int>): String {
    val newList = rotateListToHaveCurrentAtTheStart(list.indexOf(1), list.circular())
    var string = ""
    for (n in 1 until newList.size) {
        string += newList[n]
    }
    return string
}

private fun addCupsToList(input: List<Int>): MutableList<Int> {
    val newInputList = input.toMutableList()
    newInputList += 10..1000000
    return newInputList
}

data class Cup(var value: Int, var left: Cup? = null, var right: Cup? = null) {
    override fun toString(): String {
        return "{" + value +
                ", left=" + left?.value +
                ", right=" + right?.value +
                '}'
    }
}