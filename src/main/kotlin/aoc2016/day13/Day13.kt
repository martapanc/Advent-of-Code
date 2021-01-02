package aoc2016.day13

import aoc2020.day20.Coord

fun computeCoordinate(coord: Coord, favouriteNumber: Int): Char {
    val x = coord.x
    val y = coord.y
    val result = x * x + 3 * x + 2 * x * y + y + y * y + favouriteNumber
    val oneCount = Integer.toBinaryString(result).count { it == '1' }
    if (oneCount % 2 == 0)
        return '.'
    return '#'
}

fun computeGrid(favouriteNumber: Int, targetLocation: Coord): Int {
    val hiX = targetLocation.x + 100
    val hiY = targetLocation.y + 100
    val grid = mutableMapOf<Coord, Char>()
    for (y in 0..hiY)
        for (x in 0..hiX)
            grid[Coord(x, y)] = computeCoordinate(Coord(x, y), favouriteNumber)

    var edge = mutableListOf(Coord(1, 1))
    val discoveredCells = mutableMapOf(Coord(1, 1) to 0)
    var distance = 1
    while (!discoveredCells.containsKey(targetLocation)) {
        val newEdge = mutableListOf<Coord>()
        for (cell in edge) {
            val neighbors = getUndiscoveredFreeNeighbors(cell, grid, discoveredCells)
            for (neighbor in neighbors) {
                discoveredCells[neighbor] = distance
                newEdge.add(neighbor)
            }
        }
        edge = newEdge
        distance++
    }
    return discoveredCells[targetLocation]!!
}

private fun getUndiscoveredFreeNeighbors(cell: Coord, map: Map<Coord, Char>, discoveredMap: Map<Coord, Int>): List<Coord> {
    val neighbors = mutableListOf<Coord>()
    val neighborsCoordDeltas = listOf(Coord(1, 0), Coord(-1, 0), Coord(0, 1), Coord(0, -1))
    neighborsCoordDeltas
        .map { Coord(cell.x + it.x, cell.y + it.y) }
        .filterTo(neighbors) { map[it] != null && map[it] == '.' && !discoveredMap.containsKey(it)}
    return neighbors
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