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
    return findValidIps(list).first()
}

fun countValidIps(list: List<Pair<Long, Long>>): Int {
    return findValidIps(list).count()
}

private fun findValidIps(list: List<Pair<Long, Long>>): MutableList<Long> {
    val validIps = mutableListOf<Long>()
    val sorted = list.sortedBy { it.first }
    var currentRangeMax = sorted[0].second
    for (range in sorted) {
        when {
            range.first == currentRangeMax + 1 -> currentRangeMax = range.second
            range.first == currentRangeMax + 2 -> {
                validIps.add(currentRangeMax + 1)
                currentRangeMax = range.second
            }
            range.first < currentRangeMax -> {
                if (range.second > currentRangeMax) currentRangeMax = range.second
            }
        }
    }
    return validIps
}
