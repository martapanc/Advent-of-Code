package aoc2015.day18

import util.Coord

fun playGameOfLights(inputMap: Map<Coord, Char>, repeat: Int = 1, stuckCorners: Boolean = false): Int {
    var map = inputMap.toMutableMap()
    val hiX = map.maxByOrNull { it.key.x }!!.key.x
    val hiY = map.maxByOrNull { it.key.y }!!.key.y
    val corners = listOf(Coord(0, 0), Coord(0, hiY), Coord(hiX, 0), Coord(hiX, hiY))
    if (stuckCorners) {
        for (coord in corners) {
            map[coord] = '#'
        }
    }
    repeat(repeat) {
        val mapCopy = mutableMapOf<Coord, Char>()
        for (entry in map.entries) {
            if (stuckCorners && entry.key in corners) {
                mapCopy[entry.key] = '#'
            } else {
                val onCount = getNeighbors(entry.key, map).count { it == '#' }
                when (entry.value) {
                    '#' -> mapCopy[entry.key] = if (onCount == 2 || onCount == 3) '#' else '.'
                    '.' -> mapCopy[entry.key] = if (onCount == 3) '#' else '.'
                }
            }
        }
        map = mapCopy
    }
    return map.values.count { it == '#' }
}

fun getNeighbors(light: Coord, map: Map<Coord, Char>): List<Char> {
    val deltaCoords = listOf(
        Coord(-1, -1), Coord(0, -1), Coord(1, -1),
        Coord(-1, 0), Coord(1, 0),
        Coord(-1, 1), Coord(0, 1), Coord(1, 1)
    )
    return deltaCoords
        .map { Coord(light.x + it.x, light.y + it.y) }
        .filter { map[it] != null }
        .map { map[it]!! }
}
