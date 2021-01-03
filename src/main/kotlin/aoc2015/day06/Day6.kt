package aoc2015.day06

import aoc2020.day20.Coord
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
    val lightMap = mutableMapOf<Coord, Boolean>()
    for (y in 0..999) for (x in 0..999) lightMap[Coord(x, y)] = false

    for (instruction in list) {
        for (y in instruction.first.y..instruction.second.y) {
            for (x in instruction.first.x..instruction.second.x) {
                when (instruction.mode) {
                    Mode.TOGGLE -> lightMap[Coord(x, y)] = !lightMap[Coord(x, y)]!!
                    Mode.ON -> lightMap[Coord(x, y)] = true
                    Mode.OFF -> lightMap[Coord(x, y)] = false
                }
            }
        }
    }
    return lightMap.values.count { it }
}

data class Instruction(val mode: Mode, val first: Coord, val second: Coord)

enum class Mode { ON, OFF, TOGGLE }