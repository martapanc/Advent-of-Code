package aoc2020.day09

import java.io.File

fun readInputToList(path: String): List<Long> {
    val lineList = mutableListOf<Long>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it.toLong()) }
    return lineList;
}

fun findFirstNumberNotTheSumOfPreviousKNumbers(list: List<Long>, previousK: Int): Long {
    for (i in previousK until list.size - 1) {
        val number = list[i]
        val mapOfPreviousFive = buildMapFromNumberToDifference(i, previousK, list, number)
        val pairIsFound = mapOfPreviousFive.keys.any { mapOfPreviousFive.containsValue(it) }
        if (!pairIsFound) {
            return number
        }
    }
    return -1
}

// Exclude cases where key == difference (e.g. number=40 and with 20 in the previous K)
// and where difference < 0, since the input has only positive integers
private fun buildMapFromNumberToDifference(i: Int, previousK: Int, list: List<Long>, number: Long):
        MutableMap<Long, Long> {
    val mapOfPreviousFive = mutableMapOf<Long, Long>()
    (i - previousK until i)
        .filter { list[it] != number - list[it] && number - list[it] >= 0 }
        .forEach { mapOfPreviousFive[list[it]] = number - list[it] }
    return mapOfPreviousFive
}

fun findContiguousNumberGivingN(list: List<Long>, resultFromP1: Long): Long {
    var combinations = 4
    val p1Result: Long = resultFromP1

    while (combinations < list.size) {
        for (i in 0 until list.size - combinations) {
            var sum: Long = 0
            val numList = mutableListOf<Long>()
            for (j in i until i + combinations) {
                sum += list[j]
                numList.add(list[j])
            }
            if (sum == p1Result) {
                return numList.minOrNull()!! + numList.maxOrNull()!!
            }
        }
        combinations++
    }
    return -1
}