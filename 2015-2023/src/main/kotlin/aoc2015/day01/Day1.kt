package aoc2015.day01

fun runPart1(input: String): Int {
    return input.count { it == '(' } - input.count { it == ')' }
}

fun runPart2(input: String): Int {
    var currentFloor = 0
    var index = 0
    while (currentFloor >= 0) {
        if (input[index] == '(') currentFloor++ else currentFloor--
        index++
    }
    return index
}
