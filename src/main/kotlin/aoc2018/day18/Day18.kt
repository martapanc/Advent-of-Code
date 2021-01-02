package aoc2018.day18

import aoc2020.day20.Coord
import util.readInputLineByLine

fun readInputToMap(path: String): Map<Coord, Char> {
    val map = mutableMapOf<Coord, Char>()
    var x = 0
    for ((y, line) in readInputLineByLine(path).withIndex()) {
        for (char in line) {
            map[Coord(x, y)] = char
            x++
        }
        x = 0
    }
    return map
}

fun playGameOfLumberyards(inputMap: Map<Coord, Char>, minutes: Int): Int {
    var fieldMap = inputMap.toMutableMap()
    repeat(minutes) {
        fieldMap = computeNextSnapshot(fieldMap)
    }
    return fieldMap.values.count { it == '|' } * fieldMap.values.count { it == '#' }
}

fun playLongGameOfLumberyards(inputMap: Map<Coord, Char>, minutes: Long): Int {
    val firstSnapShot = gridToString(inputMap)
    val snapshotList = mutableListOf(firstSnapShot)
    var newSnapshot = inputMap.toMutableMap()
    var runCount = 1
    val cycleCount: Int
    while (true) {
        newSnapshot = computeNextSnapshot(newSnapshot)
        val newSnapshotString = gridToString(newSnapshot)
        if (snapshotList.contains(newSnapshotString)) {
            cycleCount = runCount - snapshotList.indexOf(newSnapshotString)
            break
        }
        snapshotList.add(gridToString(newSnapshot))
        runCount++
    }
    // The cycle begins after the # of total runs minus the first time an existing grid was met.
    // To compute what the grid looks like after 1 bil minutes, we can subtract the value obtained above from the minutes,
    // then add the remainder of the division diff / cycle
    val runsBeforeCycleBegins = runCount - cycleCount
    return playGameOfLumberyards(inputMap, (runsBeforeCycleBegins + (minutes - runsBeforeCycleBegins) % cycleCount).toInt())
}

private fun computeNextSnapshot(fieldMap: MutableMap<Coord, Char>): MutableMap<Coord, Char> {
    val mapCopy = fieldMap.toMutableMap()
    for (acre in fieldMap.entries) {
        val neighbors = getNeighborAcres(acre.key, fieldMap)
        when (acre.value) {
            '.' -> mapCopy[acre.key] = if (neighbors.treeCount >= 3) '|' else '.'
            '|' -> mapCopy[acre.key] = if (neighbors.lumberYardCount >= 3) '#' else '|'
            '#' -> mapCopy[acre.key] = if (neighbors.lumberYardCount >= 1 && neighbors.treeCount >= 1) '#' else '.'
        }
    }
    return mapCopy
}

fun gridToString(map: Map<Coord, Char>): String {
    var string = ""
    map.values.forEach { acre -> string += acre }
    return string
}

fun getNeighborAcres(acre: Coord, map: Map<Coord, Char>): NeighborAcres {
    val deltaCoords = listOf(
        Coord(-1, -1), Coord(0, -1), Coord(1, -1),
        Coord(-1, 0), Coord(1, 0),
        Coord(-1, 1), Coord(0, 1), Coord(1, 1)
    )
    var openCount = 0
    var treeCount = 0
    var lumberYardCount = 0
    for (delta in deltaCoords) {
        val neighbor = Coord(acre.x + delta.x, acre.y + delta.y)
        if (map[neighbor] != null) {
            when (map[neighbor]) {
                '.' -> openCount++
                '|' -> treeCount++
                '#' -> lumberYardCount++
            }
        }
    }
    return NeighborAcres(openCount, treeCount, lumberYardCount)
}

data class NeighborAcres(val openCount: Int, val treeCount: Int, val lumberYardCount: Int)
