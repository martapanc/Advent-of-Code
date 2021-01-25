/*
 * Copyright (c) 2018 by Todd Ginsberg
 */

package aoc2018.day15

import kotlin.math.abs

data class GameCoord(val x: Int, val y: Int) : Comparable<GameCoord> {

    fun distanceTo(otherX: Int, otherY: Int): Int =
        abs(x - otherX) + abs(y - otherY)

    fun distanceTo(other: GameCoord): Int =
        distanceTo(other.x, other.y)

    val up by lazy { GameCoord(x, y - 1) }
    val down by lazy { GameCoord(x, y + 1) }
    val left by lazy { GameCoord(x - 1, y) }
    val right by lazy { GameCoord(x + 1, y) }

    fun cardinalNeighbors(allowNegative: Boolean = true): List<GameCoord> =
        // Note: Generate in reading order!
        listOf(
            GameCoord(x, y - 1),
            GameCoord(x - 1, y),
            GameCoord(x + 1, y),
            GameCoord(x, y + 1)
        ).filter { allowNegative || it.x >= 0 && it.y >= 0 }

    fun neighbors(allowNegative: Boolean = true): List<GameCoord> =
        // Note: Generate in reading order!
        listOf(
            GameCoord(x - 1, y - 1),
            GameCoord(x, y - 1),
            GameCoord(x + 1, y - 1),
            GameCoord(x - 1, y),
            GameCoord(x + 1, y),
            GameCoord(x - 1, y + 1),
            GameCoord(x, y + 1),
            GameCoord(x + 1, y + 1)
        ).filter { allowNegative || it.x >= 0 && it.y >= 0 }

    override fun compareTo(other: GameCoord): Int =
        when {
            y < other.y -> -1
            y > other.y -> 1
            x < other.x -> -1
            x > other.x -> 1
            else -> 0
        }

    companion object {
        val origin = GameCoord(0, 0)

        fun of(input: String): GameCoord =
            input.split(",")
                .map { it.trim().toInt() }
                .run { GameCoord(this[0], this[1]) }
    }
}