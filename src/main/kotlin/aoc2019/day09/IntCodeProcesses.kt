package aoc2019.day09

import java.util.ArrayList
import java.util.stream.IntStream
import java.util.function.IntConsumer

object IntCodeProcesses {
    // IntCodes 1 and 2
    @JvmStatic
    fun sumAndSubtractParam(numbers: ArrayList<Long>, index: Int, opCode: Int, relativeBase: Int, map: Map<Int, Int>) {
        val input1Pos = getParamMode(1, index, relativeBase, numbers, map)
        val input2Pos = getParamMode(2, index, relativeBase, numbers, map)
        val outputPos = getParamMode(3, index, relativeBase, numbers, map)
        addMemoryIfNeeded(numbers, Math.max(Math.max(input1Pos, input2Pos), outputPos))
        if (opCode == 1) {
            numbers[outputPos] = numbers[input1Pos] + numbers[input2Pos]
        } else {
            numbers[outputPos] = numbers[input1Pos] * numbers[input2Pos]
        }
    }

    // IntCodes 3 and 4
    @JvmStatic
    fun inputAndOutputParam(
        numbers: ArrayList<Long>,
        index: Int,
        opCode: Int,
        relativeBase: Int,
        map: Map<Int, Int>,
        inputValue: Int
    ): Output {
        val outputPos = getParamMode(1, index, relativeBase, numbers, map)
        addMemoryIfNeeded(numbers, outputPos)
        return if (opCode == 3) {
            // If 3 is in position mode, the input is stored at the position of the parameter
            // It it's in relative mode, the position is the outputPos (parameter value + relative base)
            val outputIndex = if (map[1] == 2) outputPos else Math.toIntExact(numbers[index + 1])
            numbers[outputIndex] = getInput(inputValue)
            Output("", 2)
        } else {
            Output(numbers[outputPos].toString() + "", 2)
        }
    }

    // IntCodes 5 and 6
    @JvmStatic
    fun jumpIf(numbers: ArrayList<Long>, index: Int, opCode: Int, relativeBase: Int, map: Map<Int, Int>): Long {
        val input1Pos = getParamMode(1, index, relativeBase, numbers, map)
        val input2Pos = getParamMode(2, index, relativeBase, numbers, map)
        addMemoryIfNeeded(numbers, Math.max(input1Pos, input2Pos))
        if (opCode == 5) {
            if (numbers[input1Pos] != 0L) {
                return numbers[input2Pos] - index
            }
        } else {
            if (numbers[input1Pos] == 0L) {
                return numbers[input2Pos] - index
            }
        }
        return 3
    }

    // IntCodes 7 and 8
    @JvmStatic
    fun lessThanOrEquals(numbers: ArrayList<Long>, index: Int, opCode: Int, relativeBase: Int, map: Map<Int, Int>) {
        val input1Pos = getParamMode(1, index, relativeBase, numbers, map)
        val input2Pos = getParamMode(2, index, relativeBase, numbers, map)
        val outputPos = getParamMode(3, index, relativeBase, numbers, map)
        addMemoryIfNeeded(numbers, Math.max(Math.max(input1Pos, input2Pos), outputPos))
        if (opCode == 7) {
            numbers[outputPos] = if (numbers[input1Pos] < numbers[input2Pos]) 1L else 0L
        } else {
            numbers[outputPos] = if (numbers[input1Pos] == numbers[input2Pos]) 1L else 0L
        }
    }

    // IntCode 9
    @JvmStatic
    fun updateRelativeBase(numbers: ArrayList<Long>, index: Int, relativeBase: Int, map: Map<Int, Int>): Int {
        var relativeBase2 = relativeBase
        val outputPos = getParamMode(1, index, relativeBase2, numbers, map)
        addMemoryIfNeeded(numbers, outputPos)
        relativeBase2 += numbers[outputPos].toInt()
        return relativeBase2
    }

    private fun getParamMode(
        paramNum: Int,
        index: Int,
        relativeBase: Int,
        numbers: ArrayList<Long>,
        map: Map<Int, Int>
    ): Int {
        return when (map[paramNum]) {
            0 -> Math.toIntExact(numbers[index + paramNum])
            1 -> index + paramNum
            else -> Math.toIntExact(numbers[index + paramNum]) + relativeBase
        }
    }

    private fun addMemoryIfNeeded(numbers: ArrayList<Long>, maxIndex: Int) {
        if (maxIndex >= numbers.size) {
            (numbers.size..maxIndex).forEach { i: Int -> numbers.add(i, 0L) }
        }
    }

    private fun getInput(input: Int): Long {
        return input.toLong()
    }
}