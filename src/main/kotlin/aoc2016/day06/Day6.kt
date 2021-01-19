package aoc2016.day06

import util.readInputLineByLine

fun computeMessageByMostCommonCharacter(path: String): String {
    val messages = readInputLineByLine(path)
    var result = ""
    messages[0].indices
        .map { c -> messages.map { it[c] }.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key }
        .forEach { result += it }
    return result
}

fun computeMessageByLeastCommonCharacter(path: String): String {
    val messages = readInputLineByLine(path)
    var result = ""
    messages[0].indices
        .map { c -> messages.map { it[c] }.groupingBy { it }.eachCount().minByOrNull { it.value }?.key }
        .forEach { result += it }
    return result
}