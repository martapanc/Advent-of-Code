package aoc2020.day17

import java.io.File

data class Coord3d(var x: Int, var y: Int, var z: Int)

fun readInputToMap(path: String): Map<Coord3d, Char> {
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    val inputMap = mutableMapOf<Coord3d, Char>()
    var x = 0
    for ((y, line) in lineList.withIndex()) {
        line.forEach { char ->
            inputMap[Coord3d(x, y, 0)] = char
            x++
        }
        x = 0
    }
    return inputMap
}

fun runCycles(map: Map<Coord3d, Char>, cycleNumber: Int = 6): Int {
    var finalMap = computeCycle(map)
    var i = 1
    while (i++ < cycleNumber) {
        finalMap = computeCycle(finalMap)
    }
    return mapToString(finalMap).count { it == '#' }
}

fun computeCycle(inputMap: Map<Coord3d, Char>): Map<Coord3d, Char> {
    val finalMap = mutableMapOf<Coord3d, Char>()
    val inputMapCopy = extendInputMap(inputMap)
    for (cell in inputMapCopy.entries) {
        when (cell.value) {
            '#' -> {
                if (are2Or3NeighborsActive(cell.key, inputMapCopy)) finalMap[cell.key] = '#'
                else finalMap[cell.key] = '.'
            }
            '.' -> {
                if (are3NeighborsActive(cell.key, inputMapCopy)) finalMap[cell.key] = '#'
                else finalMap[cell.key] = '.'
            }
        }
    }
    return finalMap
}

private fun extendInputMap(inputMap: Map<Coord3d, Char>): MutableMap<Coord3d, Char> {
    val inputMapCopy = inputMap.toMutableMap()
    val (minX, maxX) = getMinAndMax(inputMap, 'x')
    val (minY, maxY) = getMinAndMax(inputMap, 'y')
    val (minZ, maxZ) = getMinAndMax(inputMap, 'z')
    for (z in (minZ - 1)..(maxZ + 1))
        for (y in (minY - 1)..(maxY + 1))
            for (x in (minX - 1)..(maxX + 1)) {
                if (inputMapCopy[Coord3d(x, y, z)] == null) {
                    inputMapCopy[Coord3d(x, y, z)] = '.'
                }
            }
    return inputMapCopy
}

private fun are2Or3NeighborsActive(cell: Coord3d, map: Map<Coord3d, Char>): Boolean {
    val count = getNeighbors(cell, map).count { it == '#' }
    return count == 2 || count == 3
}

private fun are3NeighborsActive(cell: Coord3d, map: Map<Coord3d, Char>): Boolean {
    return getNeighbors(cell, map).count { it == '#' } == 3
}

fun getNeighbors(cell: Coord3d, map: Map<Coord3d, Char>): List<Char> {
    val listOfNeighbors = mutableListOf<Char>()
    val listOfCombinations = mutableListOf<Coord3d>()
    for (z in -1..1)
        for (y in -1..1)
            (-1..1)
                .filterNot { it == 0 && y == 0 && z == 0 }
                .forEach { listOfCombinations.add(Coord3d(it, y, z)) }
    listOfCombinations
        .map { Coord3d(cell.x + it.x, cell.y + it.y, cell.z + it.z) }
        .filter { map[it] != null }
        .mapTo(listOfNeighbors) { map[it] ?: error("") }
    return listOfNeighbors
}

fun mapToString(finalMap: Map<Coord3d, Char>): String {
    var mapString = ""
    for (value in finalMap.values) {
        mapString += value
    }
    return mapString
}

fun printThingBackToFront(map: Map<Coord3d, Char>) {
    val (minX, maxX) = getMinAndMax(map, 'x')
    val (minY, maxY) = getMinAndMax(map, 'y')
    val (minZ, maxZ) = getMinAndMax(map, 'z')
    for (z in minZ..maxZ) {
        println("z=$z")
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                print(map[Coord3d(x, y, z)])
            }
            println()
        }
        println("\n")
    }
}

fun getMinAndMax(map: Map<Coord3d, Char>, dimension: Char): Pair<Int, Int> {
    var min = -1
    var max = -1
    when (dimension) {
        'x' -> min = map.keys.mapTo(mutableSetOf()) { it.x }.minOrNull()!!
        'y' -> min = map.keys.mapTo(mutableSetOf()) { it.y }.minOrNull()!!
        'z' -> min = map.keys.mapTo(mutableSetOf()) { it.z }.minOrNull()!!
    }
    when (dimension) {
        'x' -> max = map.keys.mapTo(mutableSetOf()) { it.x }.maxOrNull()!!
        'y' -> max = map.keys.mapTo(mutableSetOf()) { it.y }.maxOrNull()!!
        'z' -> max = map.keys.mapTo(mutableSetOf()) { it.z }.maxOrNull()!!
    }
    return Pair(min, max)
}