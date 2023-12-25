package aoc2023.day15

fun parse(lines: List<String>): List<String> {
    return lines[0].split(",")
}

fun part1(sequences: List<String>): Int {
    return sequences.sumOf { hash(it) }
}

fun part2(input: List<String>): Long {
    return 0
}

fun hash(inputString: String): Int {
    var currentValue = 0
    for (char in inputString) {
        currentValue += char.code
        currentValue *= 17
        currentValue %= 256
    }
    return currentValue
}
