package aoc2016.day03

import util.readInputLineByLine

fun countValidTriangles(path: String): Int {
    return readInputLineByLine(path)
        .map { line -> line.trim().split(Regex("\\s+")).map { it.toInt() }.sorted() }
        .count { it[0] + it[1] > it[2] }
}

fun countValidTrianglesByColumn(path: String): Int {
    var validCount = 0
    val list = readInputLineByLine(path).joinToString("").trim().split(Regex("\\s+")).map { it.toInt() }
    for (i in list.indices step 9) {
        repeat((i until i + 3)
            .map { listOf(list[it], list[it + 3], list[it + 6]).sorted() }
            .filter { it[0] + it[1] > it[2] }.size
        ) { validCount++ }
    }
    return validCount
}