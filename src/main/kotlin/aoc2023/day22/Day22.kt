package aoc2023.day22

import aoc2022.day18.Coord3d

fun parse(lines: List<String>): List<Brick> {
    val bricks = mutableListOf<Brick>()
    lines.forEach { line ->
        val split = line.split("~")
        val a = split[0].split(",").map { it.toInt() }
        val b = split[1].split(",").map { it.toInt() }
        bricks.add(
            Brick(
                Coord3d(a[0], a[1], a[2]),
                Coord3d(b[0], b[1], b[2])
            )
        )
    }
    return bricks.sortedBy { it.a.z }
}

fun part1(bricks: List<Brick>): Long {
    return 0
}

fun part2(bricks: List<Brick>): Long {
    return 0
}

data class Brick(val a: Coord3d, val b: Coord3d) {
}
