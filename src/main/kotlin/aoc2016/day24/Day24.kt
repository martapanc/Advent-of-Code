package aoc2016.day24

import aoc2020.day20.Coord
import util.findDistanceInMap

fun findMinDistanceBetweenPoints(start: Coord, end: Coord, map: Map<Coord, Char>): Int {
    val discoveredCells = findDistanceInMap(start, end, map)
    return discoveredCells[end]!!
}

fun computeDistances(grid: Map<Coord, Char>, numbers: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7)) {
    val charNums = numbers.map { it.toString().first() }
    val coordList = grid.filter { it.value in charNums }.keys
    val zeroCoord = grid.filter { it.value == '0' }.keys.first()

    val gridWithoutNumbers = mutableMapOf<Coord, Char>()
    for (coord in grid.entries) {
        gridWithoutNumbers[coord.key] = if (coord.value in charNums) '.' else coord.value
    }
    val distances = mutableMapOf<Pair<Coord, Coord>, Int>()
    for (coord in coordList) {
        distances[Pair(zeroCoord, coord)] = findMinDistanceBetweenPoints(zeroCoord, coord, gridWithoutNumbers)
    }
    for (coord in coordList) {
        coordList
            .filter { coord != it }
            .forEach { distances[Pair(coord, it)] = findMinDistanceBetweenPoints(coord, it, gridWithoutNumbers) }
    }
}