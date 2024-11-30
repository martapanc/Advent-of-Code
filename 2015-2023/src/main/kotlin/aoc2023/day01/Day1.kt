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

    list.forEach { line: String ->
        val digits = replaceStringNumberWithDigits(line).filter { it.isDigit() }
        val first = Integer.parseInt(digits[0].toString())
        val last = Integer.parseInt(digits[digits.length - 1].toString())
        sum += first * 10 + last
    }

    return sum
}


fun replaceStringNumberWithDigits(input: String): String {
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

    var result = input
    var output = ""

    while (result.isNotEmpty()) {
        for ((word, digit) in numberMap) {
            if (result[0].isDigit()) {
                output += result[0]
                break
            } else if (result.startsWith(word)) {
                output += digit.toString()
                break
            }
        }
        result = result.substring(1)
    }

    return output
}

