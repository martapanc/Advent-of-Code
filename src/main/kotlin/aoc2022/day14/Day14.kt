package aoc2022.day14

import aoc2018.day17.printTileMap
import util.Coord
import util.readInputLineByLine

fun readInputToRockPaths(path: String): MutableMap<Coord, Char> {
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

fun rockPathsToMap(coords: Set<Coord>): MutableMap<Coord, Char> {
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

fun part1(map: MutableMap<Coord, Char>): Int {
    var sandCount = 0
    val initialPos = Coord(500, 1)
    sandLands@while (true) {
        var source = Coord(initialPos.x, initialPos.y)
        var next = sandUnitFalls(map, source)
        while (source != next) {
            source = next.copy()
            next = sandUnitFalls(map, source)
            if (next.y > map.keys.maxBy { it.y }.y) {
                break@sandLands
            }
        }
        sandCount++
        map[next] = 'o'
//        printTileMap(map)
    }
    return sandCount
}

fun part2(map: MutableMap<Coord, Char>): Int {
    val floorY = map.keys.maxBy { it.y }.y + 2
    val minX = map.keys.minBy { it.x }.x - 155
    val maxX = map.keys.maxBy { it.x }.x + 105
    (minX .. maxX).forEach { x ->
        map[Coord(x, floorY)] = '#'
    }
    var sandCount = 0
    val initialPos = Coord(500, 0)
    sandLands@while (true) {
        var source = initialPos.copy()
        var next = sandUnitFalls(map, source)
        while (source != next) {
            source = next.copy()
            next = sandUnitFalls(map, source)
        }
        sandCount++
        map[next] = 'o'
        if (next == initialPos) {
            break@sandLands
        }
    }
    printTileMap(map)
    return sandCount
}

fun sandUnitFalls(map: Map<Coord, Char>, source: Coord): Coord {
    val vertical = Coord(source.x, source.y + 1)
    if (map[vertical] != '#' && map[vertical] != 'o') {
        return vertical
    }
    val slideLeft = Coord(source.x - 1, source.y + 1)
    if (map[slideLeft] != '#' && map[slideLeft] != 'o') {
        return slideLeft
    }
    val slideRight = Coord(source.x + 1, source.y + 1)
    if (map[slideRight] != '#' && map[slideRight] != 'o') {
        return slideRight
    }
    return source
}

fun Coord.copy(): Coord {
    return Coord(this.x, this.y)
}
