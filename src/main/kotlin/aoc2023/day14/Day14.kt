package aoc2023.day14

import aoc2021.day25.printMap
import util.Coord

fun part1(map: Map<Coord, Char>): Long {
    val tiltedMap = map.toMutableMap()
    for (entry in map.entries) {
        if (entry.value == 'O') {
            var north = entry.key
            while (tiltedMap[getNorthCoord(north)] != null && tiltedMap[getNorthCoord(north)] == '.') {
                north = getNorthCoord(north)
            }
            if (entry.key != north) {
                tiltedMap[entry.key] = '.'
                tiltedMap[north] = 'O'
            }
        }
    }

    printMap(tiltedMap)

    val totRows = map.keys.maxBy { it.y }.y + 1
    var sum = 0L
    tiltedMap.entries
        .filter { it.value == 'O' }
        .forEach { entry ->
            sum += (totRows - entry.key.y)
        }

    return sum
}

fun part2(map: Map<Coord, Char>): Long {
    return 0
}

private fun getNorthCoord(coord: Coord) = Coord(coord.x, coord.y - 1)