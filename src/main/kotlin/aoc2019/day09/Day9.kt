package aoc2019.day09

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.*


fun readInput(input: String): ArrayList<Long> {
    val list = ArrayList<Long>()
    val reader: BufferedReader
    try {
        reader = BufferedReader(FileReader(input))
        var line = reader.readLine()
        while (line != null) {
            Arrays.stream(line.split(",").toTypedArray()).map { s: String -> s.toLong() }
                .forEachOrdered { e: Long -> list.add(e) }
            line = reader.readLine()
        }
        reader.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return list
}


fun processInput(numbers: ArrayList<Long>, input: Int): Long {
    var i = 0
    var relativeBase = 0
    val outputBuilder = StringBuilder()
    while (i < numbers.size) {
        val opCode = Math.toIntExact(numbers[i])
        if (opCode == 99) {
            break
        }
        val output = processParameterMode(numbers, i, opCode, input, relativeBase)
        outputBuilder.append(output.code)
        if (output.relativeBase != 0) {
            relativeBase = output.relativeBase
        }
        i += output.index
    }
    return outputBuilder.toString().toLong()
}


fun processParameterMode(
    numbers: ArrayList<Long>,
    index: Int,
    opCode: Int,
    inputValue: Int,
    relativeBase: Int
): Output {
    val reducedOpCode = opCode % 100
    val parameterModeMap: MutableMap<Int, Int> = HashMap()
    parameterModeMap[1] = opCode / 100 % 10
    parameterModeMap[2] = opCode / 1000 % 10
    parameterModeMap[3] = opCode / 10000 % 10
    return when (reducedOpCode) {
        1, 2 -> {
            IntCodeProcesses.sumAndSubtractParam(
                numbers,
                index,
                reducedOpCode,
                relativeBase,
                parameterModeMap
            )
            Output("", 4)
        }
        3, 4 -> IntCodeProcesses.inputAndOutputParam(
            numbers,
            index,
            reducedOpCode,
            relativeBase,
            parameterModeMap,
            inputValue
        )
        5, 6 -> Output(
            "",
            IntCodeProcesses.jumpIf(numbers, index, reducedOpCode, relativeBase, parameterModeMap)
        )
        7, 8 -> {
            IntCodeProcesses.lessThanOrEquals(
                numbers,
                index,
                reducedOpCode,
                relativeBase,
                parameterModeMap
            )
            Output("", 4)
        }
        9 -> Output(
            "",
            2,
            IntCodeProcesses.updateRelativeBase(numbers, index, relativeBase, parameterModeMap)
        )
        else -> Output("err")
    }
}
