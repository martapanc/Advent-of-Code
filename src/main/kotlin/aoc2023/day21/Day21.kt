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

    val (dirX, dirY) = Pair(
        intArrayOf(1, 0, -1, 0),
        intArrayOf(0, 1, 0, -1)
    )

    for (y0 in 0 until height) {
        for (x0 in 0 until  width) {
            if (map[Coord(x0, y0)] == 'S') {
                var queue0 = ArrayDeque<Coord>()
                var queue1 = ArrayDeque<Coord>()
                val u = HashSet<Coord>()

                fun enqueue(x: Int, y: Int) {
                    if (map[Coord(x.mod(width), y.mod(height))] == '#')
                        return
                    val coord = Coord(x, y)
                    if (coord in u)
                        return
                    u += coord
                    queue1.add(Coord(x, y))
                }

                enqueue(x0, y0)

                fun sumDiamond(xx: Int, yy: Int, sh: Int): Long {
                    var sum = 0L
                    val x1 = x0 + xx * width + sh * stepsRem
                    val y1 = y0 + yy * height + sh * stepsRem
                    when (sh) {
                        0 -> for (x in x1 - stepsRem..x1 + stepsRem + stepsRem) {
                            val w = stepsRem - abs(x - x1)
                            for (y in y1 - w..y1 + w) if (Coord(x, y) in u) sum++
                        }

                        1 -> for (x in x1 - stepsRem + 1..x1 + stepsRem) {
                            val w = if (x <= x1) stepsRem - (x1 - x) else stepsRem - (x - x1 - 1)
                            for (y in y1 - w + 1..y1 + w) if (Coord(x, y) in u) sum++
                        }
                    }
                    return sum
                }

                val rc = Array(2) { LongArray(2) }

                fun computed(dw: Int): Long {
                    var sum = 0L
                    for (i in -dw..dw) {
                        val w = dw - abs(i)
                        val t0 = (abs(i) + w + dw) % 2
                        sum += rc[t0][0] * (w + 1) + rc[1 - t0][0] * w
                    }
                    for (i in -dw..<dw) {
                        val w = if (i < 0) dw + i + 1 else dw - i
                        sum += rc[0][1] * w + rc[1][1] * w
                    }
                    return sum
                }

                var cur = 0
                while (true) {
                    if (queue0.isEmpty()) {
                        queue0 = queue1.also { queue1 = queue0 }
                        if (cur % width == stepsRem) {
                            val rep = cur / width
                            for (sh in 0..1) for (ii in -1..0) {
                                val mod = (ii + rep).mod(2)
                                if (rc[mod][sh] == 0L) rc[mod][sh] = sumDiamond(ii, 0, sh) else check(
                                    rc[mod][sh] == sumDiamond(
                                        ii, 0, sh
                                    )
                                )
                            }
                            val all = queue0.size.toLong()
                            val computed = computed(rep)
                            check(all == computed)
                            var td = 0L
                            for (ii in -rep..rep) {
                                for (jj in -rep..rep) {
                                    val sd = sumDiamond(ii, jj, 0)
                                    td += sd
                                    print(sd.toString().padStart(8))
                                }
                                for (jj in -rep..rep) {
                                    val sd = sumDiamond(ii, jj, 1)
                                    td += sd
                                    print(sd.toString().padStart(8))
                                }
                                println()
                            }
                            check(all == td)
                            if (rep == 2) {
                                return computed(stepsInt)
                            }
                        }
                        u.clear()
                        cur++
                    }
                    val (i, j) = queue0.removeFirst()
                    for (d in 0..3) enqueue(i + dirX[d], j + dirY[d])
                }
            }
        }
    }
    return 0L
}
