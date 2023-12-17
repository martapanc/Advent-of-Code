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

fun compute(springs: String, conditions: List<Int>): String {

    val result = splitOnChange(springs);

    return ""
}
fun splitOnChange(s: String): List<String> {
    var t = s.take(1)
    for (i in 1 until s.length)
        if (t.last() == s[i]) t += s[i]
        else t += "|" + s[i]
    return t.split("|")
}

fun buildRegex(delimiters: Set<Char>): Regex {
    val escapedDelimiters = delimiters.joinToString("|") { Regex.escape(it.toString()) }
    return Regex("[$escapedDelimiters]+")
}