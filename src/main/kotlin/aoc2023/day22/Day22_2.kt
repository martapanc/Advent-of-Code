package aoc2023.day22

import aoc2022.day18.Coord3d
import util.Coord
import kotlin.math.abs
import kotlin.math.sign

fun part1_2(bricks: List<Brick>): Int {
    val gridHeight = 10
    val gridWidth = 10

    val gridHeights = Array(gridHeight) { IntArray(gridWidth) }
    val gridBrickIndices = Array(gridHeight) { IntArray(gridWidth) { -1 } }

    val canDisintegrate = BooleanArray(bricks.size) { true }

    for ((brickIndex, brick) in bricks.withIndex()) {
        val deltaX = (brick.b.x - brick.a.x).sign
        val deltaY = (brick.b.y - brick.a.y).sign
        val distanceXY = abs(brick.a.x - brick.b.x) + abs(brick.a.y - brick.b.y)
        var maxHeight = 0

        for (i in 0..distanceXY) {
            val x = brick.a.x + i * deltaX
            val y = brick.a.y + i * deltaY
            maxHeight = maxOf(maxHeight, gridHeights[x][y])
        }

        val supportingBricks = HashSet<Int>()

        for (i in 0..distanceXY) {
            val x = brick.a.x + i * deltaX
            val y = brick.a.y + i * deltaY

            if (maxHeight == gridHeights[x][y] && gridBrickIndices[x][y] >= 0)
                supportingBricks += gridBrickIndices[x][y]
        }

        if (supportingBricks.size == 1)
            canDisintegrate[supportingBricks.single()] = false

        val newHeight = maxHeight + 1 + brick.b.z - brick.a.z

        for (i in 0..distanceXY) {
            val x = brick.a.x + i * deltaX
            val y = brick.a.y + i * deltaY
            gridHeights[x][y] = newHeight
            gridBrickIndices[x][y] = brickIndex
        }
    }

    return canDisintegrate.count { it }
}



