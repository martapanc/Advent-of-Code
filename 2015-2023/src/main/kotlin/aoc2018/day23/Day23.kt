package aoc2018.day23

import util.readInputLineByLine
import kotlin.math.abs
import kotlin.math.max

fun readInputToMap(path: String): Map<BotCoord, Int> {
    val map = mutableMapOf<BotCoord, Int>()
    val regex = Regex("pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)")
    for (line in readInputLineByLine(path)) {
        val match = regex.matchEntire(line)!!.groups.map { it!!.value }.subList(1, 5).map { it.toInt() }
        val coord3d = BotCoord(match[0], match[1], match[2])
        map[coord3d] = match[3]
    }
    return map
}

fun countNanobotsInRange(map: Map<BotCoord, Int>): Int {
    val nanobotWithLargestRange = map.maxByOrNull { it.value }
    return map.entries.count {
        compute3dManhattanDistance(it.key, nanobotWithLargestRange!!.key) <= nanobotWithLargestRange.value
    }
}

fun findClosestCoordinateInRangeOfMostNanobots(map: Map<BotCoord, Int>): Int {
    val hiX = map.maxByOrNull { it.key.x }!!.key.x
    val loX = map.minByOrNull { it.key.x }!!.key.x
    val hiY = map.maxByOrNull { it.key.y }!!.key.y
    val loY = map.minByOrNull { it.key.y }!!.key.y
    val hiZ = map.maxByOrNull { it.key.z }!!.key.z
    val loZ = map.minByOrNull { it.key.z }!!.key.z
    var maxRange = 0
    val coordinates = mutableListOf<Pair<BotCoord, Int>>()
    for (z in loZ..hiZ)
        for (y in loY..hiY)
            for (x in loX..hiX) {
                var nanobotsInRange = 0
                val testCoord = BotCoord(x, y, z)
                for (nanobot in map.entries) {
                    if (compute3dManhattanDistance(testCoord, nanobot.key) <= nanobot.value) {
                        nanobotsInRange++
                    }
                }
                if (nanobotsInRange > maxRange) {
                    maxRange = nanobotsInRange
                    coordinates.add(Pair(testCoord, nanobotsInRange))
                }
            }
    return compute3dManhattanDistance(coordinates.maxByOrNull { it.second }!!.first)
}

fun findClosestCoordinateInRangeOfMostNanobotsV2(map: Map<BotCoord, Int>): Int {
    val nanobots = map.entries.map { Nanobot(it.key, it.value) }.toSet()
    val initialPos = BotCoord(0, 0, 0)
    var currentRadius = max(map.keys.deltaBy { it.x }, max(map.keys.deltaBy { it.y }, map.keys.deltaBy { it.z }))
    var currentNanobots = setOf(Nanobot(initialPos, currentRadius))

    while (currentRadius > 0) {
        currentRadius = (currentRadius / 2) + if (currentRadius > 2) 1 else 0
        val newNanobots = currentNanobots.flatMap { bot ->
            bot.pos.neighbors(currentRadius).map { coord ->
                bot.copy(pos = coord, r = currentRadius).let { newBot ->
                    newBot to nanobots.count { newBot.intersects(it) }
                }
            }
        }
        val maxDistance = newNanobots.map { it.second }.maxOrNull() ?: 0
        currentNanobots = newNanobots.filter { it.second == maxDistance }.map { it.first }.toSet()
    }
    return currentNanobots.minByOrNull { initialPos.distanceTo(it.pos) }!!.pos.distanceTo(initialPos)
}

fun compute3dManhattanDistance(target: BotCoord, source: BotCoord = BotCoord(0, 0, 0)): Int {
    return abs(target.x - source.x) +
            abs(target.y - source.y) +
            abs(target.z - source.z)
}

inline fun <T> Iterable<T>.deltaBy(block: (T) -> Int): Int {
    val values = map(block)
    return abs((values.maxOrNull() ?: 0) - (values.minOrNull() ?: 0))
}

data class BotCoord(val x: Int, val y: Int, val z: Int) {
    fun distanceTo(other: BotCoord): Int = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

    fun neighbors(delta: Int): List<BotCoord> =
        (-1..1).flatMap { xd ->
            (-1..1).flatMap { yd ->
                (-1..1).map { zd ->
                    this.copy(x = this.x + xd * delta, y = this.y + yd * delta, z = this.z + zd * delta)
                }
            }
        }
}

data class Nanobot(val pos: BotCoord, val r: Int) {
    fun intersects(other: Nanobot) =
        pos.distanceTo(other.pos) <= r + other.r
}
