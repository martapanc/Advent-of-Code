package aoc2021.day02

import java.awt.Point

fun computeSubmarinePosition(inputList: List<String>): Int {
    val initialPos = Point(0, 0)

    for (il: String in inputList) {
        val split = il.split(" ")
        val instruction = Instruction.valueOf(split[0].toUpperCase())
        val value = split[1].toInt()

        when (instruction) {
            Instruction.UP -> {
                val currentY = initialPos.getY()
                initialPos.y = currentY.toInt() - value
            }
            Instruction.DOWN -> {
                val currentY = initialPos.getY()
                initialPos.y = currentY.toInt() + value
            }
            Instruction.FORWARD -> {
                val currentX = initialPos.getX()
                initialPos.x = currentX.toInt() + value
            }
        }
    }

    return initialPos.x * initialPos.y;
}

fun computeSubmarinePositionPart2(inputList: List<String>): Int {
    val initialPos = Point(0, 0)
    var aim = 0

    for (il: String in inputList) {
        val split = il.split(" ")
        val instruction = Instruction.valueOf(split[0].toUpperCase())
        val value = split[1].toInt()

        when (instruction) {
            Instruction.UP -> {
                aim -= value
            }
            Instruction.DOWN -> {
                aim += value
            }
            Instruction.FORWARD -> {
                val currentX = initialPos.getX().toInt()
                initialPos.x = currentX + value

                val currentY = initialPos.getY().toInt()
                initialPos.y = currentY + aim * value
            }
        }
    }

    return initialPos.x * initialPos.y;
}

enum class Instruction {
    FORWARD(), UP(), DOWN()

}