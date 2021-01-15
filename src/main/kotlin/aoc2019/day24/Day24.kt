package aoc2019.day24

import aoc2020.day11.mapToString
import aoc2020.day20.Coord
import kotlin.math.pow

fun playGameOfBugs(map: Map<Coord, Char>): Long {
    var finalMap = computeRound(map)
    var mapString = mapToString(map)
    val listOfLayouts = mutableListOf<String>()
    var biodiversitySum = 0L

    do {
        listOfLayouts.add(mapString)
        finalMap = computeRound(finalMap)
        mapString = mapToString(finalMap)
    } while (!listOfLayouts.contains(mapString))

    for ((index, char) in mapString.withIndex()) {
        if (char == '#') {
            biodiversitySum += 2.0.pow(index).toLong()
        }
    }
    return biodiversitySum
}

fun playMultilayerGameOfBugs(initialMap: Map<Coord, Char>): Long {
    return -1
}

fun computeRound(inputMap: Map<Coord, Char>): Map<Coord, Char> {
    val finalMap = mutableMapOf<Coord, Char>()
    for (entry in inputMap.entries)
        when (entry.value) {
            '.' -> {
                if (areThereOneOrTwoAdjacentBugs(entry, inputMap)) finalMap[entry.key] = '#'
                else finalMap[entry.key] = '.'
            }
            '#' -> {
                if (!isThereOneAdjacentBug(entry, inputMap)) finalMap[entry.key] = '.'
                else finalMap[entry.key] = '#'
            }
        }
    return finalMap
}

fun areThereOneOrTwoAdjacentBugs(entry: Map.Entry<Coord, Char>, map: Map<Coord, Char>): Boolean {
    val count = getAdjacentSpacesContent(entry, map).count { it == '#' }
    return count == 1 || count == 2
}

fun isThereOneAdjacentBug(entry: Map.Entry<Coord, Char>, map: Map<Coord, Char>): Boolean {
    return getAdjacentSpacesContent(entry, map).count { it == '#' } == 1
}

fun getAdjacentSpacesContent(space: Map.Entry<Coord, Char>, map: Map<Coord, Char>): List<Char> {
    val adjacentSpacesContent = mutableListOf<Char>()
    val deltas = listOf(Coord(0, 1), Coord(0, -1), Coord(1, 0), Coord(-1, 0))
    deltas.mapNotNullTo(adjacentSpacesContent) {
        map[Coord(space.key.x + it.x, space.key.y + it.y)]
    }
    return adjacentSpacesContent
}
