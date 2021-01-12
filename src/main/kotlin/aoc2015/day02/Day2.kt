package aoc2015.day02

import util.readInputLineByLine

fun readInputToList(path: String): List<Sides> {
    val list = mutableListOf<Sides>()
    for (line in readInputLineByLine(path)) {
        val split = line.split("x").map { it.toInt() }.sorted()
        list.add(Sides(split[0], split[1], split[2]))
    }
    return list
}

fun computeTotalArea(list: List<Sides>): Long {
    return list
        .map { (3 * (it.h * it.w) + 2 * (it.h * it.l + it.l * it.w)).toLong() }
        .sum()
}

fun computeTotalRibbon(list: List<Sides>): Long {
    return list
        .map { (2 * (it.h + it.w) + it.h * it.l * it.w).toLong() }
        .sum()
}

class Sides(val h: Int, val w: Int, val l: Int)
