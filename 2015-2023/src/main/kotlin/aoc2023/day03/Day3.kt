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
                if (schematic[eastCoord] == null ||
                    (schematic[eastCoord] != null && !schematic[eastCoord]!!.isDigit())
                ) {
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

fun part2(schematic: Map<GameCoord, Char>): Long {
    var sum: Long = 0
    val gearCandidates = mutableListOf<GearCandidate>()

    for (y in 0..schematic.keys.maxBy { it.y }.y) {
        var currentNumber = ""
        var currentNumNeighbors = mutableMapOf<Char?, GameCoord>()
        for (x in 0..schematic.keys.maxBy { it.x }.x) {
            val currentCoord = GameCoord(x, y)
            val currChar = schematic[currentCoord]!!
            if (!currChar.isDigit()) {
                continue
            } else {
                currentNumber += currChar
                val neighborCoords = currentCoord.neighbors(false)
                neighborCoords.forEach {
                    if (schematic[it] != null) {
                        currentNumNeighbors[schematic[it]] = it
                    }
                }

                val eastCoord = GameCoord(x + 1, y)
                if (schematic[eastCoord] == null ||
                    (schematic[eastCoord] != null && !schematic[eastCoord]!!.isDigit())
                ) {
                    if (currentNumNeighbors.count { it.key == '*' } == 1) {
                        if (gearCandidates.any { it.coord == currentNumNeighbors['*'] }) {
                            val gearCandidate = gearCandidates.first { it.coord == currentNumNeighbors['*']}
                            sum += Integer.parseInt(currentNumber) * gearCandidate.number

                            gearCandidates.remove(gearCandidate)
                        } else {
                            gearCandidates.add(GearCandidate(Integer.parseInt(currentNumber), currentNumNeighbors['*']!!))
                        }
                    }

                    currentNumber = ""
                    currentNumNeighbors = mutableMapOf()
                }
            }
        }
    }
    return sum
}

data class GearCandidate(val number: Int, val coord: GameCoord)
