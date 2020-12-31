package aoc2017.day5

import util.readInputLineByLine

fun readInputToList(path: String): List<Int> {
    val inputList = mutableListOf<Int>()
    for (line in readInputLineByLine(path)) {
        inputList.add(line.toInt())
    }
    return inputList
}

fun exitMaze(list: List<Int>, isPart2: Boolean = false): Int {
    var steps = 0
    var index = 0
    val mazeList = list.toMutableList()

    while (mazeList.size > index) {
        val current = mazeList[index]
        if (isPart2 && current >= 3) {
            mazeList[index] = current - 1
        } else {
            mazeList[index] = current + 1
        }
        index += current
        steps++
    }
    return steps
}
