package aoc2019.day05

import java.lang.StringBuilder
import java.util.ArrayList
import java.util.HashMap

fun processInput(numbers: List<Int>, input: Int): Int {
    val mutableNumbers = ArrayList(numbers)
    var i = 0
    val outputBuilder = StringBuilder()
    while (i < numbers.size) {
        val opCode = numbers[i]
        if (opCode == 99) {
            break
        }
        val output = processParameterMode(mutableNumbers, i, opCode, input)
        outputBuilder.append(output.code)
        i += output.index
    }
    return outputBuilder.toString().toInt()
}

fun processParameterMode(numbers: ArrayList<Int>, index: Int, opCode: Int, inputValue: Int): Output {
    val reducedOpCode = opCode % 100
    val parameterModeMap: MutableMap<Int, Int> = HashMap()
    parameterModeMap[1] = opCode / 100 % 10
    parameterModeMap[2] = opCode / 1000 % 10
    parameterModeMap[3] = opCode / 10000 % 10
    return when (reducedOpCode) {
        1, 2 -> {
            sumAndSubtractParam(numbers, index, reducedOpCode, parameterModeMap)
            Output("", 4)
        }
        3, 4 -> inputAndOutputParam(numbers, index, reducedOpCode, parameterModeMap, inputValue)
        5, 6 -> Output("", jumpIf(numbers, index, reducedOpCode, parameterModeMap))
        7, 8 -> {
            lessThanOrEquals(numbers, index, reducedOpCode, parameterModeMap)
            Output("", 4)
        }
        else -> Output("err")
    }
}

private fun sumAndSubtractParam(numbers: ArrayList<Int>, index: Int, opCode: Int, map: Map<Int, Int>) {
    val input1Pos = numbers[index + 1]
    val input2Pos = numbers[index + 2]
    val outputPos = numbers[index + 3]
    if (opCode == 1) {
        numbers[if (map[3] == 0) outputPos else index + 3] = numbers[if (map[1] == 0) input1Pos else index + 1] +
                numbers[if (map[2] == 0) input2Pos else index + 2]
    } else {
        numbers[if (map[3] == 0) outputPos else index + 3] = numbers[if (map[1] == 0) input1Pos else index + 1] *
                numbers[if (map[2] == 0) input2Pos else index + 2]
    }
}

private fun inputAndOutputParam(
    numbers: ArrayList<Int>,
    index: Int,
    opCode: Int,
    map: Map<Int, Int>,
    inputValue: Int
): Output {
    val pos = numbers[index + 1]
    return if (opCode == 3) {
        numbers[pos] = getInput(inputValue)
        Output("", 2)
    } else {
        Output(numbers[if (map[1] == 0) pos else index + 1].toString() + "", 2)
    }
}

private fun jumpIf(numbers: ArrayList<Int>, index: Int, opCode: Int, map: Map<Int, Int>): Int {
    val input1Pos = numbers[index + 1]
    val input2Pos = numbers[index + 2]
    if (opCode == 5) {
        if (numbers[if (map[1] == 0) input1Pos else index + 1] != 0) {
            return numbers[if (map[2] == 0) input2Pos else index + 2] - index
        }
    } else {
        if (numbers[if (map[1] == 0) input1Pos else index + 1] == 0) {
            return numbers[if (map[2] == 0) input2Pos else index + 2] - index
        }
    }
    return 3
}

private fun lessThanOrEquals(numbers: ArrayList<Int>, index: Int, opCode: Int, map: Map<Int, Int>) {
    val input1Pos = numbers[index + 1]
    val input2Pos = numbers[index + 2]
    val outputPos = numbers[index + 3]
    if (opCode == 7) {
        if (numbers[if (map[1] == 0) input1Pos else index + 1] <
            numbers[if (map[2] == 0) input2Pos else index + 2]
        ) {
            numbers[outputPos] = 1
        } else {
            numbers[outputPos] = 0
        }
    } else {
        if (numbers[if (map[1] == 0) input1Pos else index + 1] ==
            numbers[if (map[2] == 0) input2Pos else index + 2]
        ) {
            numbers[outputPos] = 1
        } else {
            numbers[outputPos] = 0
        }
    }
}

private fun getInput(input: Int): Int {
    return input
}
