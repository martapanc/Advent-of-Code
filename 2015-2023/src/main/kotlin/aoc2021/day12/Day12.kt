package aoc2021.day12

import util.readInputLineByLine

fun solve(caveSystem: MutableMap<String, List<String>>, part2: Boolean = false): Int {
    var count = 0
    val queue = ArrayDeque<Triple<String, List<String>, Boolean>>()
    queue.add(Triple("start", listOf("start"), false))
    while (queue.isNotEmpty()) {
        val (curr, visited, twice) = queue.removeFirst()
        if (curr == "end") count++
        else
            caveSystem[curr]!!.forEach { next ->
                if (next !in visited) {
                    val newVisited = if (next == next.lowercase()) visited + next else visited
                    queue.add(Triple(next, newVisited, twice))
                } else if (!twice && next != "start" && part2) {
                    queue.add(Triple(next, visited, true))
                }
            }
    }
    return count
}

fun readInputToCaveSystem(path: String): MutableMap<String, List<String>> {
    val map = mutableMapOf<String, List<String>>()
    readInputLineByLine(path).map { it.split("-") }.forEach { (a, b) ->
        map[a] = (map[a] ?: listOf()) + b
        map[b] = (map[b] ?: listOf()) + a
    }
    return map
}
