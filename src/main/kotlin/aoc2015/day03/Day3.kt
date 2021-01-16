package aoc2015.day03

import aoc2020.day20.Coord

fun processTrip(string: String): Int {
    var current = Coord(0, 0)
    val houseSet = mutableSetOf(current)
    for (char in string) {
        current = getNewCoord(char, current)!!
        houseSet.add(current)
    }
    return houseSet.size
}

fun processTripPart2(string: String): Int {
    var santaCurrent = Coord(0, 0)
    var roboCurrent = Coord(0, 0)
    val houseSet = mutableSetOf(santaCurrent)
    for ((i, char) in string.withIndex()) {
        if (i % 2 == 0) {
            santaCurrent = getNewCoord(char, santaCurrent)!!
            houseSet.add(santaCurrent)
        } else {
            roboCurrent = getNewCoord(char, roboCurrent)!!
            houseSet.add(roboCurrent)
        }
    }
    return houseSet.size
}

private fun getNewCoord(char: Char, currentCoord: Coord): Coord? {
    return when (char) {
        '^' -> Coord(currentCoord.x, currentCoord.y - 1)
        'v' -> Coord(currentCoord.x, currentCoord.y + 1)
        '<' -> Coord(currentCoord.x - 1, currentCoord.y)
        '>' -> Coord(currentCoord.x + 1, currentCoord.y)
        else -> null
    }
}