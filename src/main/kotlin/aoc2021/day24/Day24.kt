package aoc2021.day24

import util.readInputLineByLine
import kotlin.math.pow

fun part1(list: List<Instruction>): Int {
    val inpCount = list.count { it.op == Operation.INP }
    val min = 1 * 10.0.pow(inpCount).toLong()
    val max = 9 * 10.0.pow(inpCount).toLong()
    for (number in min .. max) {
        verifyModelNumber(list, number.toString())
    }
    return 0
}

fun verifyModelNumber(instructions: List<Instruction>, input: String): Boolean {
    val inputList = input.toCharArray()
    val valueMap: MutableMap<Char, Int> = mutableMapOf('w' to 0, 'x' to 0, 'y' to 0, 'z' to 0)
    var currentInpIndex = 0
    instructions.forEach { instruction ->
        val a = valueMap[instruction.first]!!
        val digitOrChar = digitOrChar(instruction.second)
        val b = if (digitOrChar.first != '-') valueMap[digitOrChar.first]!! else digitOrChar.second

        valueMap[instruction.first] = when (instruction.op) {
            Operation.INP -> inputList[currentInpIndex++].digitToInt()
            Operation.ADD -> a + b
            Operation.MUL -> a * b
            Operation.DIV -> Math.floorDiv(a, b)
            Operation.MOD -> Math.floorMod(a, b)
            Operation.EQL -> if (a == b) 1 else 0
        }
    }
    return valueMap['z'] == 0
}

fun digitOrChar(input: String?): Pair<Char, Int> {
    if (input == null) {
        return Pair('-', 0)
    }
    if (input.all { char -> char.isDigit() }) {
        return Pair('-', Integer.parseInt(input))
    }
    return Pair(input.first(), 0)
}

fun part2(list: List<Instruction>): Int {
    return 0
}

fun readInputToInstructions(input: String): List<Instruction> {
    val instructions: MutableList<Instruction> = mutableListOf()
    readInputLineByLine(input).forEach { line ->
        val split = line.split(" ")
        instructions.add(
            if (split.size == 3)
                Instruction(Operation.valueOf(split[0].uppercase()), split[1].first(), split[2])
            else
                Instruction(Operation.valueOf(split[0].uppercase()), split[1].first())
        )
    }
    return instructions
}

open class Instruction(val op: Operation, val first: Char, val second: String? = null)

enum class Operation { INP, ADD, MUL, DIV, MOD, EQL }
