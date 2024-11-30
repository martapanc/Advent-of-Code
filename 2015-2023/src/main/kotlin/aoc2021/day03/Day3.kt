package aoc2021.day03

import aoc2020.day14.binaryToDecimal

fun getPowerConsumption(inputList: List<String>): Long {
    var gammaRate = ""
    var epsilonRate = ""

    for (i in 0 until inputList[0].length) {
        val (zeroCount, oneCount) = getBitCounts(inputList, i)

        if (zeroCount > oneCount) {
            gammaRate += 0
            epsilonRate += 1
        } else {
            gammaRate += 1
            epsilonRate += 0
        }
    }

    return binaryToDecimal(gammaRate) * binaryToDecimal(epsilonRate)
}

fun getLifeSupportRating(inputList: List<String>): Long {
    val oxygenNumberList = inputList.toMutableList()
    val co2NumberList = inputList.toMutableList()

    for (i in 0 until inputList[0].length) {
        updateValueList(oxygenNumberList, i)
        updateValueList(co2NumberList, i, '1', '0')
    }

    return binaryToDecimal(oxygenNumberList[0]) * binaryToDecimal(co2NumberList[0])
}

private fun updateValueList(
    valueList: MutableList<String>,
    bitPosition: Int,
    bitToKeep: Char = '0',
    bitToDiscard: Char = '1'
) {
    if (valueList.size > 1) {
        val (zeroCount, oneCount) = getBitCounts(valueList, bitPosition)

        if (zeroCount > oneCount) {
            valueList.removeAll { number -> number[bitPosition] != bitToKeep }
        } else {
            valueList.removeAll { number -> number[bitPosition] != bitToDiscard }
        }
    }
}

private fun getBitCounts(inputList: List<String>, i: Int): Pair<Int, Int> {
    val positionList = mutableListOf<Int>()
    inputList.forEach { number: String ->
        positionList.add(Character.getNumericValue(number[i]))
    }
    return Pair(positionList.count { x -> x == 0 }, positionList.count { x -> x == 1 })
}
