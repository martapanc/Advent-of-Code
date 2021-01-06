package aoc2019.day16

import util.readInputLineByLine
import java.lang.StringBuilder
import java.util.ArrayList
import java.util.stream.IntStream

fun readInputFile(path: String): List<Int> {
    return readString(readInputLineByLine(path)[0])
}

fun readString(string: String): List<Int> {
    return string.map { Character.getNumericValue(it) }
}

fun computeNthDigit(numbers: List<Int>, n: Int): Int {
    val pattern = intArrayOf(0, 1, 0, -1)
    val repeatedPattern: MutableList<Int> = ArrayList()
    var count = 1
    var index = 0
    var i = 0
    while (i++ < numbers.size + 1) {
        index %= 4
        repeatedPattern.add(pattern[index])
        if (count >= n) {
            index++
            count = 1
        } else {
            count++
        }
    }
    repeatedPattern.removeAt(0)
    return getOnesDigitOfComputation(numbers, repeatedPattern)
}

private fun getOnesDigitOfComputation(numbers: List<Int>, pattern: List<Int>): Int {
    return Math.abs(IntStream.range(0, numbers.size).map { i: Int -> numbers[i] * pattern[i] }.sum() % 10)
}

fun computePhase(numbers: List<Int>, phaseNum: Int, trim: Boolean): String {
    var count = 0
    var phase = ""
    var numberList = numbers
    while (count++ < phaseNum) {
        val sb = StringBuilder()
        for (i in 1..numberList.size) {
            sb.append(computeNthDigit(numberList, i))
        }
        phase = sb.toString()
        numberList = numberStringToList(phase)
        if (!trim) {
            println(phase)
        }
    }
    return if (trim) phase.substring(0, 8) else phase
}

fun tenThousandTimesList(numbers: List<Int>?): List<Int> {
    val tenThousandTimesList: MutableList<Int> = ArrayList()
    IntStream.range(0, 10000).mapToObj { i: Int -> numbers }.forEach { c: List<Int>? ->
        tenThousandTimesList.addAll(
            c!!
        )
    }
    return tenThousandTimesList
}

private fun numberStringToList(numbers: String): List<Int> {
    val list: MutableList<Int> = ArrayList()
    for (c in numbers.toCharArray()) {
        list.add(Character.getNumericValue(c))
    }
    return list
}