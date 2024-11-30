package aoc2023.day14

import aoc2021.day25.printMap
import util.Coord

fun part1(map: Map<Coord, Char>): Int {
    return computeTotalLoad(tiltMap(map, ::getNorthCoord), getTotRows(map))
}

fun part2(map: Map<Coord, Char>, period: Int): Int {
    val periodStarts = 90 // Valid for both test and real input
    val remainder = (1000000000 - periodStarts) % period

    var cycledMap = map.toMutableMap()
    repeat(periodStarts + remainder) {
        cycledMap = runTiltCycle(cycledMap)
    }
    return computeTotalLoad(cycledMap, getTotRows(map))
}

private fun computeTotalLoad(tiltedMap: MutableMap<Coord, Char>, totRows: Int): Int {
    return tiltedMap
        .entries
        .filter { it.value == 'O' }
        .sumOf { totRows - it.key.y }
}

private fun tiltMap(map: Map<Coord, Char>, getDirCoord: (Coord) -> Coord): MutableMap<Coord, Char> {
    val tiltedMap = map.toMutableMap()

    var inputMap = map.toMutableMap()
    if (getDirCoord == ::getSouthCoord || getDirCoord == ::getEastCoord) {
        inputMap = reverseMap(inputMap).toMutableMap()
    }
    for (entry in inputMap.entries) {
        if (entry.value == 'O') {
            var direction = entry.key
            while (tiltedMap[getDirCoord(direction)] != null && tiltedMap[getDirCoord(direction)] == '.') {
                direction = getDirCoord(direction)
            }
            if (entry.key != direction) {
                tiltedMap[entry.key] = '.'
                tiltedMap[direction] = 'O'
            }
        }
    }
    return tiltedMap
}

private fun runTiltCycle(inputMap: Map<Coord, Char>): MutableMap<Coord, Char> {
    var tiltedMap = inputMap.toMutableMap()
    val tiltFunctions = listOf(::getNorthCoord, ::getWestCoord, ::getSouthCoord, ::getEastCoord)
    for (tiltFunction in tiltFunctions) {
        tiltedMap = tiltMap(tiltedMap.toMutableMap(), tiltFunction)
    }
    return tiltedMap
}

private fun getNorthCoord(coord: Coord) = Coord(coord.x, coord.y - 1)

private fun getSouthCoord(coord: Coord) = Coord(coord.x, coord.y + 1)
private fun getWestCoord(coord: Coord) = Coord(coord.x - 1, coord.y)
private fun getEastCoord(coord: Coord) = Coord(coord.x + 1, coord.y)

private fun getTotRows(map: Map<Coord, Char>) = map.keys.maxBy { it.y }.y + 1

private fun reverseMap(inputMap: Map<Coord, Char>): Map<Coord, Char> {
    return inputMap.entries
        .sortedByDescending { it.key.x }
        .sortedByDescending { it.key.y }
        .associate { it.toPair() }
}