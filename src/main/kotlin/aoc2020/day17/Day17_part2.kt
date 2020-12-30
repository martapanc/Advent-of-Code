package aoc2020.day17

import java.io.File

data class Coord4d(var x: Int, var y: Int, var z: Int, var w: Int)

fun readInputToMap4d(path: String): Map<Coord4d, Char> {
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    val inputMap = mutableMapOf<Coord4d, Char>()
    var x = 0
    for ((y, line) in lineList.withIndex()) {
        line.forEach { char ->
            inputMap[Coord4d(x, y, 0, 0)] = char
            x++
        }
        x = 0
    }
    return inputMap
}

fun run4dCycles(map: Map<Coord4d, Char>, cycleNumber: Int = 6): Int {
    var finalMap = computeCycle4d(map)
    var i = 1
    while (i++ < cycleNumber) {
        finalMap = computeCycle4d(finalMap)
    }
    return mapToString4d(finalMap).count { it == '#' }
}

fun computeCycle4d(inputMap: Map<Coord4d, Char>): Map<Coord4d, Char> {
    val finalMap = mutableMapOf<Coord4d, Char>()
    val inputMapCopy = extendInputMap(inputMap)
    for (cell in inputMapCopy.entries) {
        when (cell.value) {
            '#' -> {
                if (are2Or3NeighborsActive4d(cell.key, inputMapCopy)) finalMap[cell.key] = '#'
                else finalMap[cell.key] = '.'
            }
            '.' -> {
                if (are3NeighborsActive4d(cell.key, inputMapCopy)) finalMap[cell.key] = '#'
                else finalMap[cell.key] = '.'
            }
        }
    }
    return finalMap
}

private fun extendInputMap(inputMap: Map<Coord4d, Char>): MutableMap<Coord4d, Char> {
    val inputMapCopy = inputMap.toMutableMap()
    val (minX, maxX) = getMinAndMax(inputMap, 'x')
    val (minY, maxY) = getMinAndMax(inputMap, 'y')
    val (minZ, maxZ) = getMinAndMax(inputMap, 'z')
    val (minW, maxW) = getMinAndMax(inputMap, 'w')
    for (w in (minW - 1)..(maxW + 1))
        for (z in (minZ - 1)..(maxZ + 1))
            for (y in (minY - 1)..(maxY + 1))
                for (x in (minX - 1)..(maxX + 1)) {
                    if (inputMapCopy[Coord4d(x, y, z, w)] == null) {
                        inputMapCopy[Coord4d(x, y, z, w)] = '.'
                    }
                }
    return inputMapCopy
}

private fun are2Or3NeighborsActive4d(cell: Coord4d, map: Map<Coord4d, Char>): Boolean {
    val count = getNeighbors4d(cell, map).count { it == '#' }
    return count == 2 || count == 3
}

private fun are3NeighborsActive4d(cell: Coord4d, map: Map<Coord4d, Char>): Boolean {
    return getNeighbors4d(cell, map).count { it == '#' } == 3
}

fun getNeighbors4d(cell: Coord4d, map: Map<Coord4d, Char>): List<Char> {
    val listOfNeighbors = mutableListOf<Char>()
    val listOfCombinations = mutableListOf<Coord4d>()
    for (w in -1..1)
        for (z in -1..1)
            for (y in -1..1)
                (-1..1)
                    .filterNot { it == 0 && y == 0 && z == 0 && w == 0}
                    .forEach { listOfCombinations.add(Coord4d(it, y, z, w)) }
    listOfCombinations
        .map { Coord4d(cell.x + it.x, cell.y + it.y, cell.z + it.z, cell.w + it.w) }
        .filter { map[it] != null }
        .mapTo(listOfNeighbors) { map[it] ?: error("") }
    return listOfNeighbors
}

fun printHorrorThingBackToFront(map: Map<Coord4d, Char>) {
    val (minX, maxX) = getMinAndMax(map, 'x')
    val (minY, maxY) = getMinAndMax(map, 'y')
    val (minZ, maxZ) = getMinAndMax(map, 'z')
    val (minW, maxW) = getMinAndMax(map, 'w')
    for (w in minW..maxW) {
        for (z in minZ..maxZ) {
            println("z=$z, w=$w")
            for (y in minY..maxY) {
                for (x in minX..maxX) {
                    print(map[Coord4d(x, y, z, w)])
                }
                println()
            }
            println("")
        }
    }
}

fun getMinAndMax(map: Map<Coord4d, Char>, dimension: Char): Pair<Int, Int> {
    var min = -1
    var max = -1
    when (dimension) {
        'x' -> min = map.keys.mapTo(mutableSetOf()) { it.x }.minOrNull()!!
        'y' -> min = map.keys.mapTo(mutableSetOf()) { it.y }.minOrNull()!!
        'z' -> min = map.keys.mapTo(mutableSetOf()) { it.z }.minOrNull()!!
        'w' -> min = map.keys.mapTo(mutableSetOf()) { it.z }.minOrNull()!!
    }
    when (dimension) {
        'x' -> max = map.keys.mapTo(mutableSetOf()) { it.x }.maxOrNull()!!
        'y' -> max = map.keys.mapTo(mutableSetOf()) { it.y }.maxOrNull()!!
        'z' -> max = map.keys.mapTo(mutableSetOf()) { it.z }.maxOrNull()!!
        'w' -> max = map.keys.mapTo(mutableSetOf()) { it.z }.maxOrNull()!!
    }
    return Pair(min, max)
}

fun mapToString4d(finalMap: Map<Coord4d, Char>): String {
    var mapString = ""
    for (value in finalMap.values) {
        mapString += value
    }
    return mapString
}