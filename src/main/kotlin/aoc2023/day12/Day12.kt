package aoc2023.day12

fun parse(lines: List<String>): List<Record> {
    val records = mutableListOf<Record>()
    lines.forEach { line ->
        val split = line.split(" ")
        val groups = split[1].split(",").map { it.toInt() }.toIntArray()
        records.add(Record(split[0], groups))
    }
    return records
}

fun part1(records: List<Record>): Long {
    var sum = 0L
    records.forEach {
        sum += solveRecord(it.springs, it.groups)
    }
    return sum
}

fun part2(input: List<Record>): Long {
    return 0
}

fun solveRecord(springs: String, groups: IntArray, currentLength: Int = 0): Int {
    if (springs.isEmpty()) {
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

    val char = springs[0]
    var total = 0

    if (char == '#' || char == '?') {
        total += solveRecord(springs.substring(1), groups, currentLength + 1)
    }
    if (char == '.' || char == '?') {
        if (currentLength == 0) {
            total += solveRecord(springs.substring(1), groups, 0)
        } else if (groups.isNotEmpty() && currentLength == groups[0]) {
            total += solveRecord(springs.substring(1), groups.copyOfRange(1, groups.size), 0)
        }
    }

    return total
}

data class Record(val springs: String, val groups: IntArray)