package util

import aoc2020.day20.Coord
import java.io.File


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