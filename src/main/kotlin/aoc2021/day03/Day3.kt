package aoc2021.day03

import aoc2020.day14.binaryToDecimal

fun getPowerConsumption(inputList: List<String>): Long {
    var gammaRate = ""
    var epsilonRate = ""

    for (i in 0 until inputList[0].length) {
        val positionList = mutableListOf<Int>()
        inputList.forEach { number: String ->
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

fun getLifeSupportRating(inputList: List<String>): Long {

    val oxygenNumberList = inputList.toMutableList()
    val co2NumberList = inputList.toMutableList()

    for (i in 0 until inputList[0].length) {
        val oxygenPositionList = mutableListOf<Int>()

        if (oxygenNumberList.size > 1) {
            oxygenNumberList.forEach { number: String ->
                oxygenPositionList.add(number[i].toString().toInt())
            }
            val zeroCount = oxygenPositionList.count { x -> x == 0 }
            val oneCount = oxygenPositionList.count { x -> x == 1 }

            if (zeroCount > oneCount) {
                oxygenNumberList.removeAll { number -> number[i] != '0' }
            } else {
                oxygenNumberList.removeAll { number -> number[i] != '1' }
            }
        }

        val co2PositionList = mutableListOf<Int>()

        if (co2NumberList.size > 1) {
            co2NumberList.forEach { number: String ->
                co2PositionList.add(number[i].toString().toInt())
            }
            val zeroCount = co2PositionList.count { x -> x == 0 }
            val oneCount = co2PositionList.count { x -> x == 1 }

            if (zeroCount > oneCount) {
                co2NumberList.removeAll { number -> number[i] != '1' }
            } else {
                co2NumberList.removeAll { number -> number[i] != '0' }
            }
        }
    }

    return binaryToDecimal(oxygenNumberList[0]) * binaryToDecimal(co2NumberList[0])
}
