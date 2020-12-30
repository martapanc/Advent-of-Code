package aoc2020.day11

import java.io.File

fun readInputToMap(path: String): Map<Pair<Int, Int>, Char> {
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    val inputMap = mutableMapOf<Pair<Int, Int>, Char>()
    var x = 0
    for ((y, line) in lineList.withIndex()) {
        line.forEach { char ->
            inputMap[Pair(x, y)] = char
            x++
        }
        x = 0
    }
    return inputMap
}

fun runRounds(map: Map<Pair<Int, Int>, Char>, isPart1: Boolean): Int {
    var finalMap = computeRound(map, isPart1)
    var mapString = mapToString(map)
    var finalMapString = mapToString(finalMap)
    while (mapString != finalMapString) {
        mapString = finalMapString
        finalMap = computeRound(finalMap, isPart1)
        finalMapString = mapToString(finalMap)
    }
    return finalMapString.count { it == '#' }
}

fun computeRound(inputMap: Map<Pair<Int, Int>, Char>, isPart1: Boolean): Map<Pair<Int, Int>, Char> {
    val emptySeatMethod = if (isPart1) ::areAdjacentSeatsFree else ::areVisibleSeatsFree
    val occupiedSeatMethod = if (isPart1) ::areFourOrMoreAdjacentSeatsOccupied else ::areFiveOrMoreVisibleSeatsOccupied
    val finalMap = mutableMapOf<Pair<Int, Int>, Char>()
    for (entry in inputMap.entries)
        when (entry.value) {
            'L' -> {
                if (emptySeatMethod(entry, inputMap)) finalMap[entry.key] = '#'
                else finalMap[entry.key] = 'L'
            }
            '#' -> {
                if (occupiedSeatMethod(entry, inputMap)) finalMap[entry.key] = 'L'
                else finalMap[entry.key] = '#'
            }
            '.' -> finalMap[entry.key] = '.'
        }
    return finalMap
}

private fun areAdjacentSeatsFree(seat: Map.Entry<Pair<Int, Int>, Char>, map: Map<Pair<Int, Int>, Char>): Boolean {
    return getAdjacentSeatsContent(seat, map).all { it == 'L' || it == '.' }
}

private fun areFourOrMoreAdjacentSeatsOccupied(seat: Map.Entry<Pair<Int, Int>, Char>, map: Map<Pair<Int, Int>, Char>): Boolean {
    return getAdjacentSeatsContent(seat, map).count { it == '#' } >= 4
}

private fun getAdjacentSeatsContent(seat: Map.Entry<Pair<Int, Int>, Char>, map: Map<Pair<Int, Int>, Char>): List<Char> {
    val adjacentSeatsContent = mutableListOf<Char>()
    val seatX = seat.key.first
    val seatY = seat.key.second
    (seatY - 1).rangeTo(seatY + 1).forEach { y ->
        (seatX - 1).rangeTo(seatX + 1)
            .filter { !(seatX == it && seatY == y) && map[Pair(it, y)] != null }
            .mapTo(adjacentSeatsContent) { map[Pair(it, y)] ?: error("") }
    }
    return adjacentSeatsContent
}

private fun areVisibleSeatsFree(seat: Map.Entry<Pair<Int, Int>, Char>, map: Map<Pair<Int, Int>, Char>): Boolean {
    return getVisibleSeatsContent(seat, map).all { it == 'L' || it == '.' }
}

private fun areFiveOrMoreVisibleSeatsOccupied(seat: Map.Entry<Pair<Int, Int>, Char>, map: Map<Pair<Int, Int>, Char>): Boolean {
    return getVisibleSeatsContent(seat, map).count { it == '#' } >= 5
}

private fun getVisibleSeatsContent(seat: Map.Entry<Pair<Int, Int>, Char>, map: Map<Pair<Int, Int>, Char>): List<Char> {
    val visibleSeatsContent = mutableListOf<Char>()
    val directions = listOf(Pair(-1, -1), Pair(0, -1), Pair(1, -1),     // NW N NE
                            Pair(-1, 0),               Pair(1, 0),      // W  .  E
                            Pair(-1, 1),  Pair(0, 1),  Pair(1, 1))      // SW S SE
    for (dir in directions) {
        var currX = seat.key.first + dir.first
        var currY = seat.key.second + dir.second
        while (map[Pair(currX, currY)] != null && map[Pair(currX, currY)] == '.') {
            currX += dir.first
            currY += dir.second
        }
        if (map[Pair(currX, currY)] != null) {
            visibleSeatsContent.add(map.getValue(Pair(currX, currY)))
        }
    }
    return visibleSeatsContent
}

fun mapToString(finalMap: Map<Pair<Int, Int>, Char>): String {
    var mapString = ""
    for (value in finalMap.values) { mapString += value }
    return mapString
}

fun printSeatMap(map: Map<Pair<Int, Int>, Char>, maxX: Int, maxY: Int) {
    for (y in 0..maxY) {
        for (x in 0..maxX) {
            print(map[Pair(x, y)])
        }
        println()
    }
    println("")
}
