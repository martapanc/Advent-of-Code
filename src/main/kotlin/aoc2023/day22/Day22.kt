package aoc2023.day22

import aoc2022.day18.Coord3d
import util.Coord

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
    val maxX = (bricks.map { it.a.x } + bricks.map { it.b.x }).maxBy { it }
    val maxY = (bricks.map { it.a.y } + bricks.map { it.b.y }).maxBy { it }
    return 0
}

fun part2(bricks: List<Brick>): Long {
    return 0
}

fun brickCanFall(brick: Brick, bricks: List<Brick>): Boolean {
    val bricksBottom = brick.bottomZ()
    // Brick's already on bottom
    if (bricksBottom == 1) {
        return false
    }
    // More than 1 layer above the highest
    if ((bricksBottom - bricks[bricks.lastIndex].bottomZ()) > 1) {
        return true
    }

    val bricksInLowerLayer = bricks.filter { it.occupiesLayerZ(bricksBottom - 1) }

    val cellsOccupiedInLowerLayer = cellsOccupiedOnLayer(bricksInLowerLayer)
    val brickCoordsInLayer = brick.getCoordinatesOnLayer()

    val intersect = cellsOccupiedInLowerLayer.intersect(brickCoordsInLayer)
    return intersect.isEmpty()
}

fun cellsOccupiedOnLayer(bricksOccupyingLayer: List<Brick>): Set<Coord> {
    val cellsOccupied = mutableSetOf<Coord>()
    bricksOccupyingLayer.forEach { brick ->
        if (brick.a.z != brick.b.z) {
            cellsOccupied.add(Coord(brick.a.x, brick.a.y))
        } else {
            if (brick.a.x == brick.b.x) { // Same X
                (brick.a.y .. brick.b.y).forEach { y ->
                    cellsOccupied.add(Coord(brick.a.x, y))
                }
            } else { // Same y
                (brick.a.x .. brick.b.x).forEach { x ->
                    cellsOccupied.add(Coord(x, brick.a.y))
                }
            }
        }
    }
    return cellsOccupied
}

data class Brick(val a: Coord3d, val b: Coord3d) {
    fun bottomZ(): Int = minOf(a.z, minOf(b.z))

    fun occupiesLayerZ(z: Int): Boolean = z in IntRange(a.z, b.z) || z in IntRange(b.z, a.z)

    fun getCoordinatesOnLayer(): Set<Coord> {
        val coords = mutableSetOf<Coord>()
        if (a.z != b.z) {
            coords.add(Coord(a.x, a.y))
        } else {
            if (a.x == b.x) {
                (a.y .. b.y).forEach { y ->
                    coords.add(Coord(a.x, y))
                }
            } else {
                (a.x .. b.x).forEach { x ->
                    coords.add(Coord(x, a.y))
                }
            }
        }
        return coords
    }
}


