package aoc2023.day03

import aoc2018.day15.GameCoord

private val symbols = setOf('*', '+', '-', '=', '#', '$', '/', '@', '%', '&')

fun parse(lines: List<String>): Map<GameCoord, Char> {
    val engineSchematic = mutableMapOf<GameCoord, Char>()
    var x = 0
    var y = 0
    lines.forEach {
        it.forEach { char ->
            engineSchematic[GameCoord(x, y)] = char
            x++
        }
        x = 0
        y++
    }

    return engineSchematic
}

fun part1(schematic: Map<GameCoord, Char>): Int {
    var sum = 0

    for (y in 0..schematic.keys.maxBy { it.y }.y) {
        var currentNumber = ""
        var currentNumNeighbors = mutableSetOf<Char>()
        for (x in 0..schematic.keys.maxBy { it.x }.x) {
            val currentCoord = GameCoord(x, y)
            val currChar = schematic[currentCoord]!!
            if (!currChar.isDigit()) {
                continue
            } else {
                currentNumber += currChar
                val neighborCoords = currentCoord.neighbors(false)
                neighborCoords.forEach {
                    schematic[it]?.let { it1 -> currentNumNeighbors.add(it1) }
                }
                val eastCoord = GameCoord(x + 1, y)
                if (schematic[eastCoord] != null && !schematic[eastCoord]!!.isDigit()) {
                    if (currentNumNeighbors.any { symbols.contains(it) }) {
                        sum += Integer.parseInt(currentNumber)
                    }

                    currentNumber = ""
                    currentNumNeighbors = mutableSetOf()
                }
            }
        }
    }
    return sum
}

fun part2(list: List<String>): Int {
    return 0
}
