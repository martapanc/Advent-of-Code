package aoc2023.day01

fun part1(list: List<String>): Int {
    var sum = 0
    list.forEach { line: String ->
        val digits = line.filter { it.isDigit() }
        val first = Integer.parseInt(digits[0].toString())
        val last = Integer.parseInt(digits[digits.length - 1].toString())
        sum += first * 10 + last
    }

    return sum
}

fun part2(list: List<String>): Int {
    return 0
}
