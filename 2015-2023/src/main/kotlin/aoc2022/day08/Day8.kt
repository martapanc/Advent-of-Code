package aoc2022.day08

import util.Coord

fun part1(grid: Map<Coord, Int>): Int {
    var visibleTreeCount = 0
    val gridSideLength = grid.maxBy { it.key.x }.key.x + 1
    val visibilityMap = mutableMapOf<Coord, Char>()

    for (depth: Int in 0..(gridSideLength / 2)) {
        if (depth == 0) {
            visibleTreeCount += gridSideLength * 4 - 4
        } else {
            getCurrentBorderCoords(depth, gridSideLength).forEach { currentCoord ->
                val neighborCoords = getHorizontalAndVerticalNeighborCoords(currentCoord, gridSideLength)
                orientation@ for (orientation in getNESWNeighbors(currentCoord, neighborCoords).entries)
                    if (orientation.value.map { grid[it]!! }.all { it < grid[currentCoord]!! }) {
                        visibilityMap[currentCoord] = 'x'
                        visibleTreeCount++
                        break@orientation
                    } else {
                        visibilityMap[currentCoord] = grid[currentCoord]!!.digitToChar()
                    }
            }
        }
    }
    return visibleTreeCount
}

fun part2(grid: Map<Coord, Int>): Int {
    var highestScenicScore = 0
    val gridSideLength = grid.maxBy { it.key.x }.key.x + 1
    for (depth: Int in 0..(gridSideLength / 2)) {
        if (depth != 0)
            getCurrentBorderCoords(depth, gridSideLength).forEach { currentCoord ->
                val neighborCoords = getHorizontalAndVerticalNeighborCoords(currentCoord, gridSideLength)
                var totalScore = 1
                for (orientation in getNESWNeighbors(currentCoord, neighborCoords).entries)
                    totalScore *= getLinearScenicScore(grid, currentCoord, orientation.value)
                if (totalScore > highestScenicScore)
                    highestScenicScore = totalScore
            }
    }
    return highestScenicScore
}


fun getCurrentBorderCoords(depth: Int, gridSideLength: Int): Set<Coord> {
    val ringCoords: MutableSet<Coord> = mutableSetOf()
    val max = gridSideLength - 1 - depth
    for (y: Int in depth..max)
        for (x: Int in depth..max)
            if (x == depth || x == max || y == depth || y == max)
                ringCoords.add(Coord(x, y))
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

fun getNESWNeighbors(currentCoord: Coord, neighborCoords: Set<Coord>): Map<Cardinal, Set<Coord>> {
    val map: MutableMap<Cardinal, Set<Coord>> = mutableMapOf(
        Cardinal.NORTH to mutableSetOf(),
        Cardinal.EAST to mutableSetOf(),
        Cardinal.SOUTH to mutableSetOf(),
        Cardinal.WEST to mutableSetOf()
    )
    neighborCoords.forEach { coord ->
        if (coord.x == currentCoord.x)
            if (coord.y < currentCoord.y) {
                val set = map[Cardinal.NORTH]!!.toMutableSet()
                set.add(coord)
                map[Cardinal.NORTH] = set
            } else {
                val set = map[Cardinal.SOUTH]!!.toMutableSet()
                set.add(coord)
                map[Cardinal.SOUTH] = set
            }
        if (coord.y == currentCoord.y)
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
    val nSet = map[Cardinal.NORTH]!!.toMutableSet()
    map[Cardinal.NORTH] = nSet.sortedByDescending { it.y }.toSet()

    val wSet = map[Cardinal.WEST]!!.toMutableSet()
    map[Cardinal.WEST] = wSet.sortedByDescending { it.x }.toSet()
    return map
}

fun getLinearScenicScore(grid: Map<Coord, Int>, currentCoord: Coord, directionCoords: Set<Coord>): Int {
    var scenicScore = 0
    for (directionCoord in directionCoords) {
        scenicScore++
        if (grid[currentCoord]!! <= grid[directionCoord]!!)
            break
    }
    return scenicScore
}

enum class Cardinal { NORTH, EAST, SOUTH, WEST }
