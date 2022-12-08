package aoc2022.day08

import aoc2020.day20.Coord

fun part1(grid: Map<Coord, Int>): Int {
    var visibleTreeCount = 0
    val visibleTrees: MutableSet<Coord> = mutableSetOf()
    val gridSideLength = grid.maxBy { it.key.x }.key.x + 1
    for (depth: Int in 0..(gridSideLength / 2)) {
        if (depth == 0) {
            visibleTreeCount += gridSideLength * 4 - 4
        } else {
            val borderCoords = getCurrentBorderCoords(depth, gridSideLength)
            borderCoords.forEach { currentCoord ->
                val neighborCoords = getHorizontalAndVerticalNeighborCoords(currentCoord, gridSideLength)
                val map = getNESWNeighborCoords(currentCoord, neighborCoords)
                orient@ for (orientation in map.entries) {
                    val neighbors = orientation.value.map { grid[it]!! }
                    if (neighbors.all { it < grid[currentCoord]!! }) {
                        visibleTrees.add(currentCoord)
                        break@orient
                    }
                }
            }
        }
    }
    return visibleTreeCount + visibleTrees.size
}

fun part2(list: List<String>): Int {
    return 0
}


fun getCurrentBorderCoords(depth: Int, gridSideLength: Int): Set<Coord> {
    val ringCoords: MutableSet<Coord> = mutableSetOf()
    val max = gridSideLength - 1 - depth
    for (y: Int in depth..max) {
        for (x: Int in depth..max) {
            if (x == depth || x == max || y == depth || y == max) {
                ringCoords.add(Coord(x, y))
            }
        }
    }
    return ringCoords
}

fun getHorizontalAndVerticalNeighborCoords(coord: Coord, gridSideLength: Int): Set<Coord> {
    val neighborCoords: MutableSet<Coord> = mutableSetOf()
    for (y: Int in 0 until gridSideLength) {
        neighborCoords.add(Coord(coord.x, y))
    }
    for (x: Int in 0 until gridSideLength) {
        neighborCoords.add(Coord(x, coord.y))
    }
    neighborCoords.remove(coord)
    return neighborCoords
}

fun getNESWNeighborCoords(currentCoord: Coord, neighborCoords: Set<Coord>): Map<Cardinal, Set<Coord>> {
    val map: MutableMap<Cardinal, Set<Coord>> = mutableMapOf(
        Cardinal.NORTH to mutableSetOf(),
        Cardinal.EAST to mutableSetOf(),
        Cardinal.SOUTH to mutableSetOf(),
        Cardinal.WEST to mutableSetOf()
    )
    neighborCoords.forEach { coord ->
        if (coord.x == currentCoord.x) {
            if (coord.y < currentCoord.y) {
                val set = map[Cardinal.NORTH]!!.toMutableSet()
                set.add(coord)
                map[Cardinal.NORTH] = set
            } else {
                val set = map[Cardinal.SOUTH]!!.toMutableSet()
                set.add(coord)
                map[Cardinal.SOUTH] = set
            }
        }
        if (coord.y == currentCoord.y) {
            if (coord.x < currentCoord.x) {
                val set = map[Cardinal.WEST]!!.toMutableSet()
                set.add(coord)
                map[Cardinal.WEST] = set
            } else {
                val set = map[Cardinal.EAST]!!.toMutableSet()
                set.add(coord)
                map[Cardinal.EAST] = set
            }
        }
    }
    return map
}

enum class Cardinal { NORTH, EAST, SOUTH, WEST }
