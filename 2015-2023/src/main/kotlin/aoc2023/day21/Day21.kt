package aoc2023.day21

import util.Coord
import kotlin.math.abs

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

fun part2(map: Map<Coord, Char>, steps: Int = 26501365): Long {
    val (height, width) = Pair(map.keys.maxBy { it.y }.y + 1, map.keys.maxBy { it.x }.x + 1)
    val stepsInt = steps / width
    val stepsRem = steps.mod(width)

    // Direction vectors for the four cardinal directions
    val (dirX, dirY) = Pair(
        intArrayOf(1, 0, -1, 0),
        intArrayOf(0, 1, 0, -1)
    )

    for (y0 in 0 until height) {
        for (x0 in 0 until width) {
            if (map[Coord(x0, y0)] == 'S') {
                var queue0 = ArrayDeque<Coord>()
                var queue1 = ArrayDeque<Coord>()
                val visited = HashSet<Coord>()

                // Enqueue a new coordinate if it's valid
                fun enqueue(x: Int, y: Int) {
                    if (map[Coord(x.mod(width), y.mod(height))] == '#')
                        return
                    val coord = Coord(x, y)
                    if (coord in visited)
                        return
                    visited += coord
                    queue1.add(Coord(x, y))
                }

                enqueue(x0, y0)

                // Calculate the sum within a diamond shape
                fun sumDiamond(xx: Int, yy: Int, shape: Int): Long {
                    var sum = 0L
                    val x1 = x0 + xx * width + shape * stepsRem
                    val y1 = y0 + yy * height + shape * stepsRem
                    when (shape) {
                        0 -> for (x in x1 - stepsRem..x1 + stepsRem + stepsRem) {
                            val diff = stepsRem - abs(x - x1)
                            for (y in y1 - diff..y1 + diff) {
                                if (Coord(x, y) in visited)
                                    sum++
                            }
                        }

                        1 -> for (x in x1 - stepsRem + 1..x1 + stepsRem) {
                            val diff = if (x <= x1) stepsRem - (x1 - x) else stepsRem - (x - x1 - 1)
                            for (y in y1 - diff + 1..y1 + diff) {
                                if (Coord(x, y) in visited)
                                    sum++
                            }
                        }
                    }
                    return sum
                }

                val ranges = Array(2) { LongArray(2) }

                // Calculate the sum within a specified range
                fun computed(diff: Int): Long {
                    var sum = 0L
                    for (i in -diff..diff) {
                        val w = diff - abs(i)
                        val t0 = (abs(i) + w + diff) % 2
                        sum += ranges[t0][0] * (w + 1) + ranges[1 - t0][0] * w
                    }
                    for (i in -diff until diff) {
                        val w = if (i < 0) diff + i + 1 else diff - i
                        sum += ranges[0][1] * w + ranges[1][1] * w
                    }
                    return sum
                }

                var current = 0
                while (true) {
                    if (queue0.isEmpty()) {
                        queue0 = queue1.also { queue1 = queue0 }
                        if (current % width == stepsRem) {
                            val rep = current / width
                            for (sh in 0..1) {
                                for (xx in -1..0) {
                                    val mod = (xx + rep).mod(2)
                                    if (ranges[mod][sh] == 0L) ranges[mod][sh] = sumDiamond(xx, 0, sh) else check(
                                        ranges[mod][sh] == sumDiamond(
                                            xx, 0, sh
                                        )
                                    )
                                }
                            }
                            val all = queue0.size.toLong()
                            val computed = computed(rep)
                            check(all == computed)
                            var td = 0L
                            for (yy in -rep..rep) {
                                for (xx in -rep..rep) {
                                    val sd = sumDiamond(xx, yy, 0)
                                    td += sd
                                }
                                for (xx in -rep..rep) {
                                    val sd = sumDiamond(xx, yy, 1)
                                    td += sd
                                }
                            }
                            check(all == td)
                            if (rep == 2) {
                                return computed(stepsInt)
                            }
                        }
                        visited.clear()
                        current++
                    }
                    val (x, y) = queue0.removeFirst()
                    for (d in 0..3)
                        enqueue(x + dirX[d], y + dirY[d])
                }
            }
        }
    }
    return 0L
}
