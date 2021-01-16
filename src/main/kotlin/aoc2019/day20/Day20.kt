package aoc2019.day20

import aoc2020.day20.Coord
import util.deltas

val regex = Regex("[A-Z]")

fun findPathFromPortalToPortals(
    startPortal: Coord,
    startLetter: Char,
    grid: Map<Coord, Char>
): Map<Coord, Int> {
    val pathMap = mutableMapOf<Coord, Int>()

    val explored = mutableListOf(startPortal)
    var edge = mutableListOf(startPortal)
    var distance = 1
    loop@ while (edge.isNotEmpty()) {
        val newEdge = mutableListOf<Coord>()
        for (point in edge)
            for (dir in deltas) {
                val coord = Coord(point.x + dir.x, point.y + dir.y)
                if (grid[coord] != null && coord !in explored && grid[coord] != '#' && (grid[coord] != startLetter || distance > 1)) {
                    explored.add(coord)
                    if (regex.matches(grid[coord].toString())) {
                        pathMap[coord] = distance
                    } else {
                        newEdge.add(coord)
                    }
                }
            }
        distance++
        edge = newEdge
    }
    return pathMap
}

fun findCoordinatesOfCellsNextToPortals(grid: Map<Coord, Char>): List<Portal> {
    val list = mutableListOf<Portal>()
    for (cell in grid.keys)
        if (grid[cell] == '.')
            for (delta in deltas) {
                val neighbor = Coord(cell.x + delta.x, cell.y + delta.y)
                if (grid[neighbor] != null && regex.matches(grid[neighbor].toString())) {
                    list.add(Portal(cell, grid[neighbor]!!))
                }
            }
    return list
}

fun mapPortalsToPortals(grid: Map<Coord, Char>) {
    val portalCells = findCoordinatesOfCellsNextToPortals(grid)
    val portalPortalDistance = mutableMapOf<Pair<Coord, Coord>, Pair<Int, Char>>()

    for (portal in portalCells) {
        val pathMap = findPathFromPortalToPortals(portal.coord, portal.letter, grid)
        for (portalEntry in pathMap.entries) {
            portalPortalDistance[Pair(portal.coord, portalEntry.key)] = Pair(portalEntry.value, portal.letter)
        }
    }

    println()
}

data class Portal(val coord: Coord, val letter: Char)