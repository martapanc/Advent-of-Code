package aoc2023.day11

import util.Coord

fun part1(galaxies: Map<Coord, Char>): Long {
    return 0
}

fun part2(galaxies: Map<Coord, Char>): Long {
    return 0
}

fun expand(galaxies: Map<Coord, Char>): Map<Coord, Char> {
    val expandedGalaxies = galaxies.toMap()
    (0 until galaxies.keys.maxBy { it.x }.x).forEach { x ->

    }
    return expandedGalaxies
}