package aoc2022.day04

import util.readInputLineByLine


fun readInputToRanges(path: String): List<Pair<Range, Range>> {
    val rangeList: MutableList<Pair<Range, Range>> = mutableListOf()
    readInputLineByLine(path).forEach { line ->
        val split = line.split(",")
        val range1 = split[0].split("-").map { Integer.parseInt(it) }
        val range2 = split[1].split("-").map { Integer.parseInt(it) }
        rangeList.add(Pair(Range(range1[0], range1[1]), Range(range2[0], range2[1])))
    }
    return rangeList
}

fun part1(rangesList: List<Pair<Range, Range>>): Int {
    var fullyContained = 0
    rangesList.forEach { rangePair ->
        if (isRangeFullyContainedInOther(rangePair)) {
            fullyContained++
        }
    }
    return fullyContained
}

fun part2(rangesList: List<Pair<Range, Range>>): Int {
    var overlapping = 0
    rangesList.forEach { rangePair ->
        if (doRangesOverlap(rangePair)) {
            overlapping++
        }
    }
    return overlapping
}

fun isRangeFullyContainedInOther(rangePair: Pair<Range, Range>): Boolean {
    val range1 = rangePair.first
    val range2 = rangePair.second
    return (range1.first >= range2.first && range1.second <= range2.second) ||
            (range1.second >= range2.second && range1.first <= range2.first)
}

fun doRangesOverlap(rangePair: Pair<Range, Range>): Boolean {
    val range1 = rangePair.first
    val range1Set = (range1.first .. range1.second).toSet()
    val range2 = rangePair.second
    val range2Set = (range2.first .. range2.second).toSet()
    return range1Set.intersect(range2Set).isNotEmpty()
}

data class Range(val first: Int, val second: Int)
