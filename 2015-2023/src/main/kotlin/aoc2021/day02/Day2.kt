package aoc2021.day02

import java.awt.Point

fun readInputToCommands(inputList: List<String>): List<SubmarineCommand> {
    val submarineCommands = mutableListOf<SubmarineCommand>()

    inputList.forEach {inputString: String ->
        val split = inputString.split(" ")
        val instruction = Instruction.valueOf(split[0].toUpperCase())
        val value = split[1].toInt()
        submarineCommands.add(SubmarineCommand(instruction, value))
    }

    return submarineCommands;
}

fun computeSubmarinePosition(commands: List<SubmarineCommand>): Int {
    val position = Point(0, 0)

    commands.forEach { command: SubmarineCommand ->
        when (command.instruction) {
            Instruction.UP -> position.y -= command.value
            Instruction.DOWN -> position.y += command.value
            Instruction.FORWARD -> position.x += command.value
        }
    }

    return position.x * position.y;
}

fun computeSubmarinePositionPart2(inputList: List<SubmarineCommand>): Int {
    val position = Point(0, 0)
    var aim = 0

    inputList.forEach { command: SubmarineCommand ->
        when (command.instruction) {
            Instruction.UP -> aim -= command.value
            Instruction.DOWN -> aim += command.value
            Instruction.FORWARD -> {
                position.x += command.value
                position.y += aim * command.value
            }
        }
    }

    return position.x * position.y;
}

enum class Instruction {
    FORWARD, UP, DOWN
}

data class SubmarineCommand(val instruction: Instruction, val value: Int)
