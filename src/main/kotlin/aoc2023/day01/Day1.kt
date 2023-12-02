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
    var sum = 0

    val numberMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    list.forEach { line: String ->
        var editedLine = line
        numberMap.entries.forEach { entry ->
            if (line.contains(entry.key)) {
                editedLine = editedLine.replace(entry.key, entry.value.toString(), true)
            }
        }

        val digits = editedLine.filter { it.isDigit() }
        val first = Integer.parseInt(digits[0].toString())
        val last = Integer.parseInt(digits[digits.length - 1].toString())
        sum += first * 10 + last
    }

    return sum
}
