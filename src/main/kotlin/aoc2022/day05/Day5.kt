package aoc2022.day05

import util.readInputLineByLine

fun readInputToMovementList(path: String): List<Movement> {
    val movements: MutableList<Movement> = mutableListOf()
    readInputLineByLine(path).forEach { line ->
        if (line.isNotEmpty() && line.startsWith("move")) {
            val split = line.replace("move ", "").replace("from ", "")
                .replace("to ", "").split(" ").map { Integer.parseInt(it)}
            movements.add(Movement(split[0], split[1] - 1, split[2] - 1))
        }
    }
    return movements
}

fun part1(stacks: List<ArrayDeque<Char>>, movements: List<Movement>): String {
    movements.forEach { movement ->
        repeat(movement.quantity) {
            val toMove = stacks[movement.from].removeLast()
            stacks[movement.to].add(toMove)
        }
    }
    return buildResult(stacks)
}

fun part2(stacks: List<ArrayDeque<Char>>, movements: List<Movement>): String {
    movements.forEach { movement ->
        val toMove: MutableList<Char> = mutableListOf()
        repeat(movement.quantity) {
            toMove.add(stacks[movement.from].removeLast())
        }
        stacks[movement.to].addAll(toMove.reversed())
    }
    return buildResult(stacks)
}

private fun buildResult(stacks: List<ArrayDeque<Char>>): String {
    var result = ""
    stacks.forEach { result += it.last() }
    return result
}

data class Movement(val quantity: Int, val from: Int, val to: Int)
