package aoc2016.day24

import aoc2020.day20.Coord
import util.findDistanceInMap

fun findMinDistanceBetweenPoints(start: Coord, end: Coord, map: Map<Coord, Char>): Int {
    val discoveredCells = findDistanceInMap(start, end, map)
    return discoveredCells[end]!!
}

fun findMinStepsToVisitAllNumbers(path: String): Int {

    return -1
}