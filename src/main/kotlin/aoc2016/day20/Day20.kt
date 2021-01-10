package aoc2016.day20

import util.readInputLineByLine

fun readInputToRangeList(path: String): List<Pair<Long, Long>> {
    val list = mutableListOf<Pair<Long, Long>>()
    for (line in readInputLineByLine(path)) {
        val pair = line.split("-").map { it.toLong() }
        list.add(Pair(pair[0], pair[1]))
    }
    return list
}

fun findMinimumIPValue(list: List<Pair<Long, Long>>): Long {
    val sorted = list.sortedBy { it.first }
    var currentRangeMax = sorted[0].second
    for (range in sorted) {
        when {
            range.first == currentRangeMax + 1 -> currentRangeMax = range.second
            range.first == currentRangeMax + 2 -> return currentRangeMax + 1
            range.first < currentRangeMax -> {
                if (range.second > currentRangeMax) {
                    currentRangeMax = range.second
                }
            }
        }
    }
    return 0L
}