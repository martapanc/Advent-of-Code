package aoc2022.day14

import aoc2018.day17.printTileMap
import util.Coord
import util.readInputLineByLine

fun readInputToRockPaths(path: String): Map<Coord, Char> {
    val rockPathCoords = mutableSetOf<Coord>()
    readInputLineByLine(path).forEach { line ->
        val coords = line.split(" -> ").map { coord ->
            val split = coord.split(",")
            Coord(split[0].toInt(), split[1].toInt())
        }
        (0 until coords.size - 1).forEach { index ->
            val first = coords[index]
            val second = coords[index + 1]
            if (first.x == second.x) {
                if (first.y < second.y) {
                    (first.y .. second.y).forEach { y -> rockPathCoords.add(Coord(first.x, y)) }
                } else {
                    (second.y .. first.y).forEach { y -> rockPathCoords.add(Coord(first.x, y)) }
                }
            } else {
                if (first.x < second.x) {
                    (first.x .. second.x).forEach { x -> rockPathCoords.add(Coord(x, first.y)) }
                } else {
                    (second.x .. first.x).forEach { x -> rockPathCoords.add(Coord(x, first.y)) }
                }
            }
        }
    }
    return rockPathsToMap(rockPathCoords)
}

fun rockPathsToMap(coords: Set<Coord>): Map<Coord, Char> {
    val minX = coords.minBy { it.x }.x
    val maxX = coords.maxBy { it.x }.x
    val minY = coords.minBy { it.y }.y
    val maxY = coords.maxBy { it.y }.y
    val map = mutableMapOf<Coord, Char>()
    (minY .. maxY).forEach { y ->
        (minX .. maxX).forEach { x ->
            if (coords.contains(Coord(x, y))) {
                map[Coord(x, y)] = '#'
            } else {
                map[Coord(x, y)] = '.'
            }
        }
    }
    map[Coord(500, 0)] = '+'
    printTileMap(map)
    return map
}

fun part1(map: Map<Coord, Char>): Int {
    return 0
}

fun part2(map: Map<Coord, Char>): Int {
    return 0
}
