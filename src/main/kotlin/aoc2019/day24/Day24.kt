package aoc2019.day24

import aoc2020.day11.mapToString
import kotlin.math.pow

fun runRounds(map: Map<Pair<Int, Int>, Char>): Long {
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

fun computeRound(inputMap: Map<Pair<Int, Int>, Char>): Map<Pair<Int, Int>, Char> {
    val finalMap = mutableMapOf<Pair<Int, Int>, Char>()
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

fun areThereOneOrTwoAdjacentBugs(entry: Map.Entry<Pair<Int, Int>, Char>, map: Map<Pair<Int, Int>, Char>): Boolean {
    val count = getAdjacentSpacesContent(entry, map).count { it == '#' }
    return count == 1 || count == 2
}

fun isThereOneAdjacentBug(entry: Map.Entry<Pair<Int, Int>, Char>, map: Map<Pair<Int, Int>, Char>): Boolean {
    return getAdjacentSpacesContent(entry, map).count { it == '#' } == 1
}

fun getAdjacentSpacesContent(space: Map.Entry<Pair<Int, Int>, Char>, map: Map<Pair<Int, Int>, Char>): List<Char> {
    val adjacentSpacesContent = mutableListOf<Char>()
    val adjacentCoordinates = listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0))
    adjacentCoordinates.mapNotNullTo(adjacentSpacesContent) {
        map[Pair(space.key.first + it.first, space.key.second + it.second)]
    }
    return adjacentSpacesContent
}
