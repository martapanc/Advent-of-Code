package aoc2016.day13

import aoc2020.day20.Coord
import util.findDistanceInMap
import util.getUndiscoveredFreeNeighbors

fun computeManhattanDistanceOfTarget(favouriteNumber: Int, targetLocation: Coord): Int {
    val grid = generateGrid(targetLocation, favouriteNumber, 10)
    val discoveredCells = findDistanceInMap(Coord(1, 1), targetLocation, grid)
    return discoveredCells[targetLocation]!!
}

fun computeReachableCellsAtDistance(favouriteNumber: Int, maxDistance: Int): Int {
    val grid = generateGrid(Coord(1, 1), favouriteNumber, 30)
    var edge = mutableListOf(Coord(1, 1))
    val discoveredCells = mutableMapOf(Coord(1, 1) to 0)
    var distance = 0
    while (distance < maxDistance) {
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
    return discoveredCells.size
}

private fun generateGrid(targetLocation: Coord, favouriteNumber: Int, delta: Int): Map<Coord, Char> {
    val hiX = targetLocation.x + delta
    val hiY = targetLocation.y + delta
    val grid = mutableMapOf<Coord, Char>()
    for (y in 0..hiY)
        for (x in 0..hiX)
            grid[Coord(x, y)] = computeCoordinate(Coord(x, y), favouriteNumber)
    return grid
}

fun computeCoordinate(coord: Coord, favouriteNumber: Int): Char {
    val x = coord.x
    val y = coord.y
    val result = x * x + 3 * x + 2 * x * y + y + y * y + favouriteNumber
    val oneCount = Integer.toBinaryString(result).count { it == '1' }
    if (oneCount % 2 == 0)
        return '.'
    return '#'
}

private fun printGrid(grid: Map<Coord, Char>, hiX: Int, hiY: Int, start: Coord, target: Coord) {
    for (y in 0..hiY) {
        for (x in 0..hiX) {
            if (Coord(x, y) == start || Coord(x, y) == target) {
                print("X")
            } else print(grid[Coord(x, y)])
        }
        println()
    }
}