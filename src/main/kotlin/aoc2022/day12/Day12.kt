package aoc2022.day12

import util.Coord
import util.deltas


fun part1(grid: Map<Coord, Char>): Int {
    val startCoord = grid.entries.first { it.value == 'S' }.key
    val endCoord = grid.entries.first { it.value == 'E' }.key
    return bfs(grid, startCoord, endCoord)
}

fun part2(grid: Map<Coord, Char>): Int {
    return 0
}

fun bfs(grid: Map<Coord, Char>, source: Coord, destination: Coord): Int {
    val queue = ArrayDeque<Distance>()
    queue.add(Distance(0, source))
    val visited = mutableSetOf<Coord>()

    while (queue.isNotEmpty()) {
        val distance = queue.removeFirst()
        if (distance.coord == destination) {
            return distance.fromStart
        }
        if (distance.coord !in visited) {
            visited.add(distance.coord)
            for (neighbor in getValidNeighbors(grid, distance.coord)) {
                if (neighbor in visited)
                    continue
                queue.addLast(Distance(distance.fromStart + 1, neighbor))
            }
        }
    }
    return -1
}

fun getValidNeighbors(grid: Map<Coord, Char>, currentCoord: Coord): Set<Coord> {
    val validNeighbors = mutableSetOf<Coord>()
    val neighbors = deltas.map { Coord(currentCoord.x + it.x, currentCoord.y + it.y) }
    neighbors.forEach { neighbor ->
        val neighborCell = grid[neighbor]
        val currentCell = grid[currentCoord]!!
        if (neighborCell != null)
            if ((currentCell == 'S' && neighborCell == 'a')
                || (currentCell == 'z' && neighborCell == 'E')
                || (currentCell + 1 == neighborCell || (neighborCell != 'E' && currentCell >= neighborCell))
            ) {
                validNeighbors.add(neighbor)
            }
    }
    return validNeighbors
}

data class Distance(val fromStart: Int, val coord: Coord)
