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

    if (checkEast(map, start, edge)) {
        newlyVisited.add(start.east())
    }
    if (checkWest(map, start, edge)) {
        newlyVisited.add(start.west())
    }
    if (checkNorth(map, start, edge)) {
        newlyVisited.add(start.north())
    }
    if (checkSouth(map, start, edge)) {
        newlyVisited.add(start.south())
    }

    while (newlyVisited.size == 2) {
        edge.addAll(newlyVisited)
        val newEdge = newlyVisited.toList()
        newlyVisited.clear()

        newEdge.forEach{
            if (checkEast(map, it, edge)) {
                newlyVisited.add(it.east())
            }
            if (checkWest(map, it, edge)) {
                newlyVisited.add(it.west())
            }
            if (checkNorth(map, it, edge)) {
                newlyVisited.add(it.north())
            }
            if (checkSouth(map, it, edge)) {
                newlyVisited.add(it.south())
            }
        }
        distance++
    }

    return distance
}

private fun checkSouth(
    map: Map<Coord, Char>,
    start: Coord,
    edge: MutableSet<Coord>
) = southSections.contains(map[start.south()]) && !edge.contains(start.south())

private fun checkNorth(
    map: Map<Coord, Char>,
    start: Coord,
    edge: MutableSet<Coord>
) = northSections.contains(map[start.north()]) && !edge.contains(start.north())

private fun checkWest(map: Map<Coord, Char>, curr: Coord, edge: MutableSet<Coord>): Boolean {
    return westSections.contains(map[curr.west()]) && !edge.contains(curr.west())
}

private fun checkEast(map: Map<Coord, Char>, curr: Coord, edge: MutableSet<Coord>): Boolean {
    return eastSections.contains(map[curr.east()]) && !edge.contains(curr.east())
}

fun part2(map: Map<Coord, Char>): Long {
    return 0
}
