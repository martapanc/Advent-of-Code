package aoc2023.day12

import util.readInputLineByLine
import java.util.*

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

fun part2(records: List<Record>): Long {
    var sum = 0L
    records.forEach {
        val unfoldedRecord = unfoldRecord(it)
        sum += solveRecord(unfoldedRecord.springs, unfoldedRecord.groups)
    }
    return sum
}
enum class CacheKey {
    SPRINGS, GROUPS, CURRENT_LENGTH
}

val cache: MutableMap<EnumMap<CacheKey, Any>, Int> = mutableMapOf()

fun solveRecord(springs: String, groups: IntArray, currentLength: Int = 0): Int {
    val key = EnumMap<CacheKey, Any>(CacheKey::class.java)
    key[CacheKey.SPRINGS] = springs
    key[CacheKey.GROUPS] = groups.toList().toIntArray()
    key[CacheKey.CURRENT_LENGTH] = currentLength

    if (cache.containsKey(key)) {
        return cache[key]!!
    }

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

    cache[key] = total
    return total
}

fun unfoldRecord(record: Record): Record {
    val unfoldedRecord = buildString {
        repeat(5) {
            append(record.springs)
            if (it < 4) {
                append('?')
            }
        }
    }
    val unfoldedGroups = List(5) { record.groups.toList() }.flatten().toIntArray()
    return Record(unfoldedRecord, unfoldedGroups)
}

data class Record(val springs: String, val groups: IntArray)

fun main() {
    val testInputLines = readInputLineByLine("src/main/kotlin/aoc2023/day12/assets/input0")
    val testInput = parse(testInputLines)

    val inputLines = readInputLineByLine("src/main/kotlin/aoc2023/day12/assets/input")
    val input = parse(inputLines)

    println(part2(testInput))
    println(part2(input))
}