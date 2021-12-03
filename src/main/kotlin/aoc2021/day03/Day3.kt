package aoc2021.day03

import aoc2015.day21.Character
import aoc2020.day14.binaryToDecimal

fun getPowerConsumption(inputList: List<String>): Long {
    var gammaRate = ""
    var epsilonRate = ""

    for (i in 0 until inputList[0].length) {
        val positionList = mutableListOf<Int>()
        inputList.forEach{ number: String ->
            positionList.add(number[i].toString().toInt())
        }
        val zeroCount = positionList.count { x -> x == 0 }
        val oneCount = positionList.count { x -> x == 1 }

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