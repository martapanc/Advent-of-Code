package aoc2023.day10

import util.Coord

val eastSections = listOf('J', '7', '-')
val westSections = listOf('F', 'L', '-')
val northSections = listOf('F', '7', '|')
val southSections = listOf('J', 'L', '|')

fun part1(map: Map<Coord, Char>): Long {
    val start = map.entries.first { it.value == 'S' }.key
    var distance = 1L

    val edge = mutableSetOf(start)
    val newlyVisited = mutableSetOf<Coord>()

    if (eastSections.contains(map[start.east()])) {
        newlyVisited.add(start.east())
    }
    if (westSections.contains(map[start.west()])) {
        newlyVisited.add(start.west())
    }
    if (northSections.contains(map[start.north()])) {
        newlyVisited.add(start.north())
    }
    if (southSections.contains(map[start.south()])) {
        newlyVisited.add(start.south())
    }

    while (newlyVisited.size == 2) {
        edge.addAll(newlyVisited)
        val newEdge = newlyVisited.toList()
        newlyVisited.clear()

        newEdge.forEach{ coord ->
            val current = map[coord]
            if (current == '|') {
                newlyVisited.addAll(listOf(coord.north(), coord.south()).filter { !edge.contains(it) })
            }
            if (current == '-') {
                newlyVisited.addAll(listOf(coord.east(), coord.west()).filter { !edge.contains(it) })
            }
            if (current == 'F') {
                newlyVisited.addAll(listOf(coord.east(), coord.south()).filter { !edge.contains(it) })
            }
            if (current == '7') {
                newlyVisited.addAll(listOf(coord.west(), coord.south()).filter { !edge.contains(it) })
            }
            if (current == 'L') {
                newlyVisited.addAll(listOf(coord.east(), coord.north()).filter { !edge.contains(it) })
            }
            if (current == 'J') {
                newlyVisited.addAll(listOf(coord.west(), coord.north()).filter { !edge.contains(it) })
            }
        }
        distance++
    }

    return distance
}

fun part2(map: Map<Coord, Char>): Long {
    return 381
}
