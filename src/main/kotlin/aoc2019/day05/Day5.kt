package aoc2019.day05

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.*


// 1: sum values at addresses (1) and (2), stores result at address (3)
// 2: multiply values at addresses (1) and (2), stores result at address (3)
// 3: read input and save it at address (1)
// 4: output value of address (1)
// 5: (1) != 0 set the pointer to the value of (2)
// 6: (1) == 0 set the pointer to the value of (2)
// 7: ((1) < (2) ? store 1 : store 0 ) at value of (3)
// 8: ((1) == (2) ? store 1 : store 0 ) at value of (3)
// 0 = position mode
// 1 = immediate mode
// Parameters that an instruction writes to will never be in immediate mode.
// PARAMETER MODE DETAILS:
// - Immediate mode for 4 (104) should output the value of the parameter (e.g. 104,0 -> out: 0). Otherwise it should output the
//      value at index 0 (e.g. 3,...,104,0 -> out: 3)
// - 5,6: 105 - (1) and (2) can be in immediate mode
// - 7,8: 1107 - only (1) and (2) can be in immediate mode
// The pointer should increment based on the number of parameters (e.g. 4 for Codes 1 and 2, 2 for Codes 3 and 4),
// except for 5 and 6 where the pointer is updated as described
fun readInput(input: String): ArrayList<Int> {
    val list = ArrayList<Int>()
    val reader: BufferedReader
    try {
        reader = BufferedReader(FileReader(input))
        var line = reader.readLine()
        while (line != null) {
            Arrays.stream(line.split(",").toTypedArray()).map { s: String -> s.toInt() }
                .forEachOrdered { e: Int? -> list.add(e!!) }
            line = reader.readLine()
        }
        reader.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return list
}

fun processInput(numbers: ArrayList<Int>, input: Int): Int {
    var i = 0
    val outputBuilder = StringBuilder()
    while (i < numbers.size) {
        val opCode = numbers[i]
        if (opCode == 99) {
            break
        }
        val output = processParameterMode(numbers, i, opCode, input)
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
