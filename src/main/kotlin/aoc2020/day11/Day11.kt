package aoc2020.day11

import aoc2020.day20.Coord

fun runRounds(map: Map<Coord, Char>, isPart1: Boolean): Int {
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

fun computeRound(inputMap: Map<Coord, Char>, isPart1: Boolean): Map<Coord, Char> {
    val emptySeatMethod = if (isPart1) ::areAdjacentSeatsFree else ::areVisibleSeatsFree
    val occupiedSeatMethod = if (isPart1) ::areFourOrMoreAdjacentSeatsOccupied else ::areFiveOrMoreVisibleSeatsOccupied
    val finalMap = mutableMapOf<Coord, Char>()
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

private fun areAdjacentSeatsFree(seat: Map.Entry<Coord, Char>, map: Map<Coord, Char>): Boolean {
    return getAdjacentSeatsContent(seat, map).all { it == 'L' || it == '.' }
}

private fun areFourOrMoreAdjacentSeatsOccupied(seat: Map.Entry<Coord, Char>, map: Map<Coord, Char>): Boolean {
    return getAdjacentSeatsContent(seat, map).count { it == '#' } >= 4
}

private fun getAdjacentSeatsContent(seat: Map.Entry<Coord, Char>, map: Map<Coord, Char>): List<Char> {
    val adjacentSeatsContent = mutableListOf<Char>()
    val seatX = seat.key.x
    val seatY = seat.key.y
    (seatY - 1).rangeTo(seatY + 1).forEach { y ->
        (seatX - 1).rangeTo(seatX + 1)
            .filter { !(seatX == it && seatY == y) && map[Coord(it, y)] != null }
            .mapTo(adjacentSeatsContent) { map[Coord(it, y)] ?: error("") }
    }
    return adjacentSeatsContent
}

private fun areVisibleSeatsFree(seat: Map.Entry<Coord, Char>, map: Map<Coord, Char>): Boolean {
    return getVisibleSeatsContent(seat, map).all { it == 'L' || it == '.' }
}

private fun areFiveOrMoreVisibleSeatsOccupied(seat: Map.Entry<Coord, Char>, map: Map<Coord, Char>): Boolean {
    return getVisibleSeatsContent(seat, map).count { it == '#' } >= 5
}

private fun getVisibleSeatsContent(seat: Map.Entry<Coord, Char>, map: Map<Coord, Char>): List<Char> {
    val visibleSeatsContent = mutableListOf<Char>()
    val directions = listOf(Coord(-1, -1), Coord(0, -1), Coord(1, -1),     // NW N NE
                            Coord(-1, 0),                       Coord(1, 0),     // W  .  E
                            Coord(-1, 1),  Coord(0, 1),  Coord(1, 1))      // SW S SE
    for (dir in directions) {
        var currX = seat.key.x + dir.x
        var currY = seat.key.y + dir.y
        while (map[Coord(currX, currY)] != null && map[Coord(currX, currY)] == '.') {
            currX += dir.x
            currY += dir.y
        }
        if (map[Coord(currX, currY)] != null) {
            visibleSeatsContent.add(map.getValue(Coord(currX, currY)))
        }
    }
    return visibleSeatsContent
}

fun mapToString(finalMap: Map<Coord, Char>): String {
    var mapString = ""
    for (value in finalMap.values) { mapString += value }
    return mapString
}

fun printSeatMap(map: Map<Coord, Char>, maxX: Int, maxY: Int) {
    for (y in 0..maxY) {
        for (x in 0..maxX) {
            print(map[Coord(x, y)])
        }
        println()
    }
    println("")
}
