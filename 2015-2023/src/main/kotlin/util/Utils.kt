package util

import aoc2020.day14.binaryToDecimal
import java.io.File
import kotlin.math.abs


val deltas = listOf(Coord(0, 1), Coord(0, -1), Coord(1, 0), Coord(-1, 0))

fun readInputLineByLine(path: String): List<String> {
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    return lineList
}

fun readInputToMap(path: String): Map<Coord, Char> {
    val map = mutableMapOf<Coord, Char>()
    var x = 0
    for ((y, line) in readInputLineByLine(path).withIndex()) {
        for (char in line) {
            map[Coord(x, y)] = char
            x++
        }
        x = 0
    }
    return map
}

fun findDistanceInMap(start: Coord, end: Coord, grid: Map<Coord, Char>): MutableMap<Coord, Int> {
    var edge = mutableListOf(start)
    val discoveredCells = mutableMapOf(start to 0)
    var distance = 1
    while (!discoveredCells.containsKey(end)) {
        val newEdge = mutableListOf<Coord>()
        for (cell in edge) {
            for (neighbor in getUndiscoveredFreeNeighbors(cell, grid, discoveredCells)) {
                discoveredCells[neighbor] = distance
                newEdge.add(neighbor)
            }
        }
        edge = newEdge
        distance++
    }
    return discoveredCells
}

fun getUndiscoveredFreeNeighbors(cell: Coord, map: Map<Coord, Char>, discoveredMap: Map<Coord, Int>): List<Coord> {
    val neighbors = mutableListOf<Coord>()
    val neighborsCoordDeltas = listOf(Coord(1, 0), Coord(-1, 0), Coord(0, 1), Coord(0, -1))
    neighborsCoordDeltas
        .map { Coord(cell.x + it.x, cell.y + it.y) }
        .filterTo(neighbors) { map[it] != null && map[it] == '.' && !discoveredMap.containsKey(it)}
    return neighbors
}

fun not(y: Int): Int {
    val binary = y.toString(2).padStart(16, '0')
    var result = ""
    for (i in binary.indices) {
        result += if (binary[i] == '1') 0 else 1
    }
    return binaryToDecimal(result).toInt()
}

fun lcm(a: Long, b: Long): Long {
    return a * b / gcd(a, b)
}

fun gcd(a: Long, b: Long): Long {
    if (a == 0L) return b
    return gcd(b % a, a)
}

fun lcm(values: List<Long>): Long {
    var result = values[0]
    values.indices.forEach { index ->
        result = lcm(result, values[index])
    }
    return result
}

data class Coord(var x: Int, var y: Int) {

    fun neighbors(allowNegative: Boolean = false): List<Coord> =
        listOf(
            Coord(x, y - 1),
            Coord(x - 1, y),
            Coord(x + 1, y),
            Coord(x, y + 1)
        ).filter { allowNegative || it.x >= 0 && it.y >= 0 }


    fun xNeighbors(allowNegative: Boolean = false): List<Coord> =
        listOf(
            Coord(x - 1, y),
            Coord(x + 1, y),
        ).filter { allowNegative || it.x >= 0 && it.y >= 0 }

    fun yNeighbors(allowNegative: Boolean = false): List<Coord> =
        listOf(
            Coord(x, y - 1),
            Coord(x, y + 1)
        ).filter { allowNegative || it.x >= 0 && it.y >= 0 }

    fun east(): Coord = Coord(x + 1, y)
    fun west(): Coord = Coord(x - 1, y)
    fun north(): Coord = Coord(x, y - 1)
    fun south(): Coord = Coord(x, y + 1)

    operator fun plus(other: Coord) = Coord(x + other.x, y + other.y)
    operator fun not() = Coord(-x, -y)

    fun manhattanDistance(coord: Coord): Int = abs(this.x - coord.x) + abs(this.y - coord.y)
}
