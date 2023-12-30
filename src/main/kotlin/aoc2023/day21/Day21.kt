package aoc2023.day21

import util.Coord

fun part1(map: Map<Coord, Char>, steps: Int = 64): Int {
    val start = map.entries.first { it.value == 'S' }.key
    var edge = start.neighbors()
        .filter { map[it] == '.' }
        .toSet()

    repeat(steps - 1) {
        val newEdge = mutableSetOf<Coord>()
        for (plot in edge) {
            for (neighbor in plot.neighbors()) {
                if (map[neighbor] == '.' || map[neighbor] == 'S') {
                    newEdge.add(neighbor)
                }
            }
        }
        edge = newEdge
    }
    return edge.size
}

fun part2(map: Map<Coord, Char>, steps: Int = 26501365): Int {
    return part1(map, steps)
}
