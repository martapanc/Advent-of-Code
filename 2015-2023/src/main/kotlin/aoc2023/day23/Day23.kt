package aoc2023.day23

import java.io.File
import java.util.*

fun parse(lines: List<String>): List<String> {
    val output = mutableListOf<String>()
    lines.forEach { line ->
        
    }
    return output
}

fun part1(input: List<String>): Long {
    return 0
}


fun part2(input: List<String>): Long {
    return 0
}


fun neighbors(grid: List<String>, r: Int, c: Int, ignoreSlopes: Boolean): List<Pair<Int, Int>> {
    val cell = grid[r][c]

    return if (ignoreSlopes || cell == '.') {
        listOf(Pair(r + 1, c), Pair(r - 1, c), Pair(r, c + 1), Pair(r, c - 1))
            .filter { (nr, nc) -> grid[nr][nc] != '#' }
    } else {
        when (cell) {
            'v' -> listOf(Pair(r + 1, c))
            '^' -> listOf(Pair(r - 1, c))
            '>' -> listOf(Pair(r, c + 1))
            '<' -> listOf(Pair(r, c - 1))
            else -> emptyList()
        }
    }
}

fun numNeighbors(grid: List<String>, r: Int, c: Int, ignoreSlopes: Boolean): Int {
    return if (ignoreSlopes || grid[r][c] == '.') {
        listOf(Pair(r + 1, c), Pair(r - 1, c), Pair(r, c + 1), Pair(r, c - 1))
            .count { (nr, nc) -> grid[nr][nc] != '#' }
    } else {
        1
    }
}

fun isNode(grid: List<String>, rc: Pair<Int, Int>, src: Pair<Int, Int>, dst: Pair<Int, Int>, ignoreSlopes: Boolean): Boolean {
    return rc == src || rc == dst || numNeighbors(grid, rc.first, rc.second, ignoreSlopes) > 2
}

fun adjacentNodes(grid: List<String>, rc: Pair<Int, Int>, src: Pair<Int, Int>, dst: Pair<Int, Int>, ignoreSlopes: Boolean): List<Pair<Pair<Int, Int>, Int>> {
    val q = LinkedList<Pair<Pair<Int, Int>, Int>>().apply { add(Pair(rc, 0)) }
    val seen = mutableSetOf<Pair<Int, Int>>()

    val result = mutableListOf<Pair<Pair<Int, Int>, Int>>()

    while (q.isNotEmpty()) {
        val (current, dist) = q.poll()
        seen.add(current)

        for ((nr, nc) in neighbors(grid, current.first, current.second, ignoreSlopes)) {
            if (nr to nc in seen) continue

            if (isNode(grid, nr to nc, src, dst, ignoreSlopes)) {
                result.add(nr to nc to dist + 1)
                continue
            }

            q.add(nr to nc to dist + 1)
        }
    }

    return result
}

fun graphFromGrid(grid: List<String>, src: Pair<Int, Int>, dst: Pair<Int, Int>, ignoreSlopes: Boolean = false): Map<Pair<Int, Int>, List<Pair<Pair<Int, Int>, Int>>> {
    val g = mutableMapOf<Pair<Int, Int>, MutableList<Pair<Pair<Int, Int>, Int>>>()
    val q = LinkedList<Pair<Int, Int>>().apply { add(src) }
    val seen = mutableSetOf<Pair<Int, Int>>()

    while (q.isNotEmpty()) {
        val rc = q.poll()
        if (rc in seen) continue

        seen.add(rc)

        for ((neighbor, weight) in adjacentNodes(grid, rc, src, dst, ignoreSlopes)) {
            g.computeIfAbsent(rc) { mutableListOf() }.add(neighbor to weight)
            q.add(neighbor)
        }
    }

    return g
}

fun longestPath(g: Map<Pair<Int, Int>, List<Pair<Pair<Int, Int>, Int>>>, cur: Pair<Int, Int>, dst: Pair<Int, Int>, distance: Int = 0, seen: MutableSet<Pair<Int, Int>> = mutableSetOf()): Int {
    if (cur == dst) return distance

    var best = 0
    seen.add(cur)

    for ((neighbor, weight) in g[cur] ?: emptyList()) {
        if (neighbor in seen) continue

        best = maxOf(best, longestPath(g, neighbor, dst, distance + weight))
    }

    seen.remove(cur)
    return best
}

fun main(args: Array<String>) {
    val inputPath = if (args.isNotEmpty()) args[0] else "src/main/kotlin/aoc2023/day23/assets/input"
    val lines = File(inputPath).readLines()

    val grid = lines.map { it }
    val height = grid.size
    val width = grid[0].length

//    grid[0][1] = '#'
//    grid[height - 1][width - 2] = '#'

    val src = Pair(1, 1)
    val dst = Pair(height - 2, width - 2)

    val g = graphFromGrid(grid, src, dst)
    val pathlen = longestPath(g, src, dst) + 2
    println("Part 1: $pathlen")

    val gIgnoreSlopes = graphFromGrid(grid, src, dst, true)
//    val pathlenIgnoreSlopes = longestPath(gIgnoreSlopes, src, dst) + 2
//    println("Part 2: $pathlenIgnoreSlopes")
}
