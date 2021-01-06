package aoc2019.day16

import util.readInputLineByLine
import java.lang.Math.abs

fun readInputToString(path: String): String {
    return readInputLineByLine(path)[0]
}

fun computeSignal(input: String, phases: Int = 100, isOffset: Boolean = false): String {
    var inputSignal = input
    var offset = 0
    if (isOffset) {
        inputSignal = repeatInput10000Times(inputSignal)
        offset = getOffset(input)
    }
    repeat(phases) {
        var phase = ""
        for (i in inputSignal.indices) {
            val phasedPattern = computePhasedPattern(i, inputSignal)
            var sum = 0
            for ((index, c) in inputSignal.withIndex()) {
                sum += phasedPattern[index] * Character.getNumericValue(c)
            }
            phase += kotlin.math.abs(sum) % 10
        }
        inputSignal = phase
    }
    return inputSignal.substring(offset, offset + 8)
}

fun computePhasedPattern(index: Int, inputSignal: String): List<Int> {
    val phasedPattern = mutableListOf<Int>()
    listOf(0, 1, 0, -1).forEach { patternItem ->
        repeat(index + 1) {
            phasedPattern.add(patternItem)
        }
    }
    val repeatedList = phasedPattern.toMutableList()
    repeat(inputSignal.length / phasedPattern.size) {
        repeatedList.addAll(phasedPattern)
    }
    return repeatedList.subList(1, inputSignal.length + 1)
}

fun repeatInput10000Times(input: String): String {
    return input.repeat(10000)
}

fun getOffset(input: String): Int {
    return input.substring(0, 7).toInt()
}

fun part2(inputString: String, times: Int = 100): String {
    val input: List<Int> = inputString.map { Character.digit(it, 10) }
    val offset = input.take(7).fold(0) { acc, digit -> 10 * acc + digit }
    val length = input.size
    val newLength = 10000 * length
    check(offset < newLength && newLength <= 2 * offset)
    val value = (offset until newLength).map { input[it % length] }.toIntArray()
    repeat(times) {
        value.indices.reversed().fold(0) { acc, i ->
            (kotlin.math.abs(acc + value[i]) % 10).also { value[i] = it }
        }
    }
    return value.take(8).joinToString("")
}