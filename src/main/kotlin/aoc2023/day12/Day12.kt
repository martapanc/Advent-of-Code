package aoc2023.day12

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

fun splitOnChange(s: String): List<String> {
    var t = s.take(1)
    for (i in 1 until s.length)
        if (t.last() == s[i]) t += s[i]
        else t += "|" + s[i]
    return t.split("|")
}

fun solveRecord(record: String, groups: IntArray, currentLength: Int = 0): Int {
    if (record.isEmpty()) {
        if (groups.isEmpty() && currentLength == 0) {
            return 1
        }
        if (groups.size == 1 && currentLength == groups[0]) {
            return 1
        }
        return 0
    }

    if ((groups.isNotEmpty() && currentLength > groups[0]) || (groups.isEmpty() && currentLength > 0)) {
        return 0
    }

    val char = record[0]
    var total = 0

    if (char == '#' || char == '?') {
        total += solveRecord(record.substring(1), groups, currentLength + 1)
    }
    if (char == '.' || char == '?') {
        if (currentLength == 0) {
            total += solveRecord(record.substring(1), groups, 0)
        } else if (groups.isNotEmpty() && currentLength == groups[0]) {
            total += solveRecord(record.substring(1), groups.copyOfRange(1, groups.size), 0)
        }
    }

    return total
}