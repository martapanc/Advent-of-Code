package aoc2023.day13

import util.Coord

fun parse(lines: List<String>): List<Map<Coord, Char>> {
    val output = mutableListOf<Map<Coord, Char>>()
    var x = 0
    var y = 0
    var map = mutableMapOf<Coord, Char>()
    lines.forEach { line ->
        if (line.isEmpty()) {
            y = 0
            output.add(map)
            map = mutableMapOf()
        } else {
            line.forEach { char ->
                map[Coord(x, y)] = char
                x++
            }
            y++
        }
        x = 0
    }
    return output
}

fun part1(maps: List<Map<Coord, Char>>): Long {
    var sum = 0L
    maps.forEach {
        sum += findMirror(it)
    }
    return sum
}

fun part2(maps: List<Map<Coord, Char>>): Long {
    var sum = 0L
    maps.forEach {
        sum += findImperfectMirror(it)
    }
    return sum
}

fun findMirror(map: Map<Coord, Char>): Int {
    val (maxX, maxY) = findMaxCoords(map)

    // Horizontal
    val groupedByY = map.keys.groupBy { it.y }
    val rows = groupedByY.keys.sorted().map { y ->
        groupedByY[y]?.sortedBy { it.x }?.map { map[it] }?.joinToString("") ?: ""
    }

    val consecutiveRowIndices = findConsecutiveIdenticalIndices(rows)
    consecutiveRowIndices.forEach { pair ->
        if (isMirror(pair, maxY, rows)) {
            return 100 * pair.second
        }
    }

    // Vertical
    val groupedByX = map.keys.groupBy { it.x }
    val columns = groupedByX.keys.sorted().map { x ->
        groupedByX[x]?.sortedBy { it.y }?.map { map[it] }?.joinToString("") ?: ""
    }

    val consecutiveColIndices = findConsecutiveIdenticalIndices(columns)
    consecutiveColIndices.forEach { pair ->
        if (isMirror(pair, maxX, columns)) {
            return pair.second
        }
    }

    throw Exception()
}

private fun isMirror(pair: Pair<Int, Int>, max: Int, strings: List<String>): Boolean {
    var isMirror = true
    var a = pair.first - 1
    var b = pair.second + 1
    mirror@ while (a >= 0 && b <= max) {
        if (strings[a] != strings[b]) {
            isMirror = false
            break@mirror
        }
        a--
        b++
    }
    return isMirror
}

fun findImperfectMirror(map: Map<Coord, Char>): Int {
    val (maxX, maxY) = findMaxCoords(map)

    // Horizontal
    val groupedByY = map.keys.groupBy { it.y }
    val rows = groupedByY.keys.sorted().map { y ->
        groupedByY[y]?.sortedBy { it.x }?.map { map[it] }?.joinToString("") ?: ""
    }

    findConsecutiveAlmostIdenticalIndices(rows).forEach { pair ->
        val (isMirror, hasSmudgeBeenFound) = isImperfectMirror(pair, maxY, rows)
        if (isMirror && hasSmudgeBeenFound) {
            return 100 * pair.second
        }
    }

    // Vertical
    val groupedByX = map.keys.groupBy { it.x }
    val columns = groupedByX.keys.sorted().map { x ->
        groupedByX[x]?.sortedBy { it.y }?.map { map[it] }?.joinToString("") ?: ""
    }

    findConsecutiveAlmostIdenticalIndices(columns).forEach { pair ->
        val (isMirror, hasSmudgeBeenFound) = isImperfectMirror(pair, maxX, columns)
        if (isMirror && hasSmudgeBeenFound) {
            return pair.second
        }
    }

    throw Exception()
}

private fun isImperfectMirror(pair: StrPair, max: Int, strings: List<String>): Pair<Boolean, Boolean> {
    var isMirror = true
    var hasSmudgeBeenFound = pair.almostIdentical
    var a = pair.first - 1
    var b = pair.second + 1
    mirror@ while (a >= 0 && b <= max) {
        if (strings[a] == strings[b] || isAlmostIdentical(strings[a], strings[b])) {
            if (isAlmostIdentical(strings[a], strings[b])) {
                if (hasSmudgeBeenFound) {
                    isMirror = false
                    break@mirror
                } else {
                    hasSmudgeBeenFound = true
                }
            }
        } else {
            isMirror = false
            break@mirror
        }
        a--
        b++
    }
    return Pair(isMirror, hasSmudgeBeenFound)
}

private fun findMaxCoords(map: Map<Coord, Char>): Pair<Int, Int> {
    return Pair(map.keys.maxBy { it.x }.x, map.keys.maxBy { it.y }.y)
}

fun isAlmostIdentical(a: String, b: String): Boolean {
    if (a == b) {
        return false
    }
    var diffCount = 0
    for (i in a.indices) {
        if (a[i] != b[i]) {
            diffCount++
            if (diffCount > 1) {
                return false
            }
        }
    }
    return diffCount == 1
}

fun findConsecutiveIdenticalIndices(strings: List<String>): List<Pair<Int, Int>> {
    val consecutiveIdenticalIndices = mutableListOf<Pair<Int, Int>>()
    for (i in 0 until strings.size - 1) {
        if (strings[i] == strings[i + 1]) {
            consecutiveIdenticalIndices.add(Pair(i, i + 1))
        }
    }

    return consecutiveIdenticalIndices
}

fun findConsecutiveAlmostIdenticalIndices(strings: List<String>): List<StrPair> {
    val consecutiveIdenticalIndices = mutableListOf<StrPair>()
    for (i in 0 until strings.size - 1) {
        if (strings[i] == strings[i + 1]) {
            consecutiveIdenticalIndices.add(StrPair(i, i + 1, false))
        }
        if (isAlmostIdentical(strings[i], strings[i + 1])) {
            consecutiveIdenticalIndices.add(StrPair(i, i + 1, true))
        }
    }

    return consecutiveIdenticalIndices
}

data class StrPair(val first: Int, val second: Int, val almostIdentical: Boolean)