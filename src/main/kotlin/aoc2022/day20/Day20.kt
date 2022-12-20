package aoc2022.day20

import util.readInputLineByLine


fun readInputToNumbers(path: String): List<Int> = readInputLineByLine(path).map { it.toInt() }

fun part1(numbers: List<Int>): Int {
    val size = numbers.size
    val sortedNumbers = numbers.toMutableList()
    numbers.forEach { num ->
        val currIndex = sortedNumbers.indexOf(num)
        val targetIndex = (sortedNumbers[currIndex] + currIndex) % size
        if (num > 0) {
            var newIndex = targetIndex % size
            newIndex = if (newIndex > currIndex) newIndex - 1 else newIndex
            val numToMove = sortedNumbers.removeAt(currIndex)
            sortedNumbers.add(newIndex, numToMove)
        } else {
            var newIndex = targetIndex % size
            newIndex = if (newIndex > currIndex) newIndex + 1 else newIndex
            val numToMove = sortedNumbers.removeAt(currIndex)
            sortedNumbers.add(newIndex, numToMove)
        }

    }
    val indexOfZero = sortedNumbers.indexOf(0)
    val pos1000 = (indexOfZero + 1000) % size
    val pos2000 = (indexOfZero + 2000) % size
    val pos3000 = (indexOfZero + 3000) % size
    return sortedNumbers[pos1000] + sortedNumbers[pos2000] + sortedNumbers[pos3000]
}

fun part2(numbers: List<Int>): Int {
    return 0
}
