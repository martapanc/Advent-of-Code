package aoc2015.day04

import aoc2016.day05.md5

fun findMinMd5Hash(input: String, isPart2: Boolean = false): Int {
    var count = 1
    var hash = md5(input + count)
    val prefix = if (isPart2) "0".repeat(6) else "0".repeat(5)
    while (!hash.startsWith(prefix)) {
        count++
        hash = md5(input + count)
    }
    return count
}