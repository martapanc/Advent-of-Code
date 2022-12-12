package aoc2015.day06

import util.Coord
import util.readInputLineByLine

fun readInputToInstructionList(path: String): List<Instruction> {
    val instructionList = mutableListOf<Instruction>()
    for (line in readInputLineByLine(path)) {
        val mode = when {
            line.contains("turn on") -> Mode.ON
            line.contains("turn off") -> Mode.OFF
            line.contains("toggle") -> Mode.TOGGLE
            else -> Mode.ON
        }
        val temp = line.replace("turn on", "").replace("turn off", "").replace("toggle", "")
        val first = temp.substringBefore(" through").substringAfter("").split(",")
        val second = temp.substringAfter("through ").split(",")
        instructionList.add(
            Instruction(
                mode,
                Coord(first[0].trim().toInt(), first[1].trim().toInt()),
                Coord(second[0].trim().toInt(), second[1].trim().toInt())
            )
        )
    }
    return instructionList
}

fun buildGrid(list: List<Instruction>): Int {
    val lightGrid = Array(1000) { BooleanArray(1000) }
    for (instruction in list)
        for (y in instruction.first.y..instruction.second.y)
            for (x in instruction.first.x..instruction.second.x) {
                when (instruction.mode) {
                    Mode.TOGGLE -> lightGrid[x][y] = !lightGrid[x][y]
                    Mode.ON -> lightGrid[x][y] = true
                    Mode.OFF -> lightGrid[x][y] = false
                }
            }
    var litCount = 0
    for (y in 0..999) repeat((0..999).filter { lightGrid[it][y] }.size) { litCount++ }
    return litCount
}

fun buildGridV2(list: List<Instruction>): Int {
    val lightGrid = Array(1000) { IntArray(1000) }
    for (instruction in list)
        for (y in instruction.first.y..instruction.second.y)
            for (x in instruction.first.x..instruction.second.x) {
                val brightness = lightGrid[x][y]
                when (instruction.mode) {
                    Mode.TOGGLE -> lightGrid[x][y] = brightness + 2
                    Mode.ON -> lightGrid[x][y] = brightness + 1
                    Mode.OFF -> lightGrid[x][y] = if (brightness > 0) brightness - 1 else 0
                }
            }
    var litCount = 0
    for (y in 0..999) for (x in 0..999) litCount += lightGrid[x][y]
    return litCount
}

data class Instruction(val mode: Mode, val first: Coord, val second: Coord)

enum class Mode { ON, OFF, TOGGLE }
