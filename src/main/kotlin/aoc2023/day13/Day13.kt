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

fun part2(input: List<Map<Coord, Char>>): Long {
    return 0
}

fun findMirror(map: Map<Coord, Char>): Int {
    val maxX = map.keys.maxBy { it.x }.x
    val maxY = map.keys.maxBy { it.y }.y

    // Horizontal
    val groupedByY = map.keys.groupBy { it.y }
    val sortedYKeys = groupedByY.keys.sorted()
    val rows = sortedYKeys.map { y ->
        groupedByY[y]?.sortedBy { it.x }?.map { map[it] }?.joinToString("") ?: ""
    }

    val consecutiveRowIndices = findConsecutiveIdenticalIndices(rows)
    consecutiveRowIndices.forEach { pair ->
        var isMirror = true
        var a = pair.first - 1
        var b = pair.second + 1
        mirror@while (a >= 0 && b <= maxY) {
            if (rows[a] != rows[b]) {
                isMirror = false
                break@mirror
            }
            a--
            b++
        }

        if (isMirror) {
            return 100 * pair.second
        }
    }

    // Vertical
    val groupedByX = map.keys.groupBy { it.x }
    val sortedKeys = groupedByX.keys.sorted()
    val columns = sortedKeys.map { x ->
        groupedByX[x]?.sortedBy { it.y }?.map { map[it] }?.joinToString("") ?: ""
    }

    val consecutiveColIndices = findConsecutiveIdenticalIndices(columns)
    consecutiveColIndices.forEach { pair ->
        var isMirror = true
        var a = pair.first - 1
        var b = pair.second + 1
        mirror@while (a >= 0 && b <= maxX) {
            if (columns[a] != columns[b]) {
                isMirror = false
                break@mirror
            }
            a--
            b++
        }

        if (isMirror) {
            return pair.second
        }
    }

    return -1
}

fun equals(list1: List<Pair<Int, Int>>, list2: List<Pair<Int, Int>>): Boolean {
    return list1.size == list2.size && list1.containsAll(list2)
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