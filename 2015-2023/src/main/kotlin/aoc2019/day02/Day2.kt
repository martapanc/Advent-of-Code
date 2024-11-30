package aoc2019.day02

import util.readInputLineByLine
import java.util.*


fun readInput(input: String): List<Int> {
    val list = mutableListOf<Int>()
    for (line in readInputLineByLine(input)) {
        list.addAll(line.split(",").map { it.toInt() })
    }
    return list
}

fun processInput(numbers: List<Int>): Int {
    val mutableNumbers = numbers.toMutableList()
    var i = 0
    while (i < mutableNumbers.size) {
        val key = mutableNumbers[i]
        if (key == 99) {
            break
        }
        val input1Pos = mutableNumbers[i + 1]
        val input2Pos = mutableNumbers[i + 2]
        val outputPos = mutableNumbers[i + 3]
        if (key == 1) {
            mutableNumbers[outputPos] = mutableNumbers[input1Pos] + mutableNumbers[input2Pos]
        }
        if (key == 2) {
            mutableNumbers[outputPos] = mutableNumbers[input1Pos] * mutableNumbers[input2Pos]
        }
        i += 4
    }
    return mutableNumbers[0]
}

fun findPair(numbers: List<Int>): Int {
    for (a in 0..99) {
        for (b in 0..99) {
            val newNumbers = ArrayList(numbers)
            newNumbers[1] = a
            newNumbers[2] = b
            if (processInput(newNumbers) == 19690720) {
                return 100 * a + b
            }
        }
    }
    return numbers[0]
}
