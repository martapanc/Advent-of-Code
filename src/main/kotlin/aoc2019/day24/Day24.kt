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

fun playMultilayerGameOfBugs(initialMap: Map<Coord, Char>, rounds: Int = 1): Int {
    val initialMapLayered = initialMap.toMutableMap()
    initialMapLayered[Coord(2, 2)] = '?'
    var layerList = mutableListOf(initialMapLayered.toMap())

    for (r in 1..rounds) {
        if (r % 2 == 1) {
            layerList.add(0, createEmptyMap())
            layerList.add(createEmptyMap())
        }
        val layerListCopy = mutableListOf<Map<Coord, Char>>()
        for ((i, layer) in layerList.withIndex()) {
            val layerCopy = createEmptyMap().toMutableMap()
            for (coord in layer.entries) {
                val parent = if (i == 0) null else layerList[i - 1]
                val child = if (i == layerList.size - 1) null else layerList[i + 1]
                val neighboringBugs = findNeighborsAcrossMultipleLayers(coord.key, parent, layerList[i], child).count { it == '#' }
                when (coord.value) {
                    '#' -> layerCopy[coord.key] = if (neighboringBugs == 1) '#' else '.'
                    '.' -> layerCopy[coord.key] = if (neighboringBugs == 1 || neighboringBugs == 2) '#' else '.'
                }
            }
            layerListCopy.add(layerCopy)
        }
        layerList = layerListCopy
    }

    var bugCount = 0
    for (map in layerList) {
        bugCount += map.values.count { it == '#' }
    }
    return bugCount
}

fun findNeighborsAcrossMultipleLayers(
    coord: Coord,
    parentMap: Map<Coord, Char>?,
    currentMap: Map<Coord, Char>,
    childMap: Map<Coord, Char>?
): List<Char> {
    val external = mutableSetOf<Coord>()
    for (i in 0..4) {
        external.add(Coord(0, i))
        external.add(Coord(4, i))
        external.add(Coord(i, 0))
        external.add(Coord(i, 4))
    }
    val internal = mutableListOf(Coord(2, 1), Coord(2, 3), Coord(1, 2), Coord(3, 2))
    val normalDeltas = listOf(Coord(1, 0), Coord(-1, 0), Coord(0, 1), Coord(0, -1))
    val internalToExternal = mutableMapOf<Coord, List<Coord>>()
    internalToExternal[Coord(2, 1)] = (0..4).map { Coord(it, 0) }
    internalToExternal[Coord(2, 3)] = (0..4).map { Coord(it, 4) }
    internalToExternal[Coord(1, 2)] = (0..4).map { Coord(0, it) }
    internalToExternal[Coord(3, 2)] = (0..4).map { Coord(4, it) }
    internalToExternal.toList()

    val neighbors = mutableListOf<Char>()
    when (coord) {
        in external -> {
            if (parentMap == null) return listOf()
            for (delta in normalDeltas) {
                val deltaCoord = Coord(coord.x + delta.x, coord.y + delta.y)
                if (currentMap[deltaCoord] == null) {
                    when {
                        coord.x == 0 -> neighbors.add(parentMap[Coord(1, 2)]!!)
                        coord.x == 4 -> neighbors.add(parentMap[Coord(3, 2)]!!)
                        coord.y == 0 -> neighbors.add(parentMap[Coord(2, 1)]!!)
                        coord.y == 4 -> neighbors.add(parentMap[Coord(2, 3)]!!)
                    }
                } else {
                    neighbors.add(currentMap[deltaCoord]!!)
                }
            }
        }
        in internal -> {
            if (childMap == null) return listOf()
            for (delta in normalDeltas) {
                val deltaCoord = Coord(coord.x + delta.x, coord.y + delta.y)
                if (currentMap[deltaCoord] == '?') {
                    val childNeighborCoords = internalToExternal[coord]!!
                    for (c in childNeighborCoords) {
                        neighbors.add(childMap[c]!!)
                    }
                } else {
                    neighbors.add(currentMap[deltaCoord]!!)
                }
            }
        }
        else -> {
            normalDeltas
                .map { Coord(coord.x + it.x, coord.y + it.y) }
                .mapTo(neighbors) { currentMap[it]!! }
        }
    }
    return neighbors
}

fun createEmptyMap(): Map<Coord, Char> {
    val map = mutableMapOf<Coord, Char>()
    for (y in 0..4) for (x in 0..4) map[Coord(x, y)] = '.'
    map[Coord(2, 2)] = '?'
    return map
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
