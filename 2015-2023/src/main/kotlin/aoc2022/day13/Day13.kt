package aoc2022.day13

import org.apache.commons.lang3.StringUtils.isNumeric
import util.readInputLineByLine

fun readInputToPairs(path: String): List<Pair<String, String>> {
    val list = mutableListOf<Pair<String, String>>()
    val inputList = readInputLineByLine(path)
    (inputList.indices step 3).forEach { index ->
        list.add(Pair(inputList[index], inputList[index+1]))
    }
    return list
}

fun part1(list: List<Pair<String, String>>): Int {
    var indexSum = 0
    list.forEachIndexed { index, pair ->
        if (isPairOrdered(pair.first, pair.second) < 0) {
            indexSum += index
        }
    }
    return indexSum
}

private fun isPairOrdered(first: String, second: String): Int {
    val firstIsNumber = isNumeric(first)
    val secondIsNumber = isNumeric(second)

    if (firstIsNumber && secondIsNumber) {
        return first.toInt() - second.toInt()
    }

    if (firstIsNumber != secondIsNumber) {
        return if (firstIsNumber) {
            isPairOrdered(String.format("[%s]", first), second)
        } else {
            isPairOrdered(first, String.format("[%s]", second))
        }
    }

    return 0
}

fun part2(list: List<Pair<String, String>>): Int {
    return 0
}

fun countNumbers(string: String): Int = stringToNumList(string).count()

fun countOpenBraces(string: String): Int = string.count { it == '[' }

fun stringToNumList(string: String): List<Int> {
    return string.replace("[", "").replace("]", "").split(",")
        .filter { it.isNotEmpty() }.map { it.toInt() }
}
