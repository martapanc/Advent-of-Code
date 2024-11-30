package aoc2021.day24

import util.readInputLineByLine


fun part1(list: List<Instruction>): Long {
    val inpCount = list.count { it.op == Operation.INP }
    val min = "8".repeat(inpCount).toLong()
    val max = "9".repeat(inpCount).toLong()
    var largestModel: Long = 0
    for (number in max downTo  min) {
        if (verifyModelNumber(list, number.toString())) {
            largestModel = number
            break
        }
    }
    return largestModel
}

fun verifyModelNumber(instructions: List<Instruction>, input: String): Boolean {
    if (input.any { it == '0' }) {
        return false
    }
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
    println(valueMap['z'])
    return valueMap['z'] == 0
}

fun solve(input: List<String>): Pair<String, String> {
    val digits = mutableMapOf<Int, Pair<Int, Int>>()
    val stack = mutableListOf<Pair<Int, Int>>()
    var (push, sub, dig) = Triple(false, 0, 0)
    input.forEachIndexed { i, line ->
        val operand = line.substringAfterLast(" ")
        if (i % 18 == 4) push = operand == "1"
        if (i % 18 == 5) sub = operand.toInt()
        if (i % 18 == 15) {
            if (push) {
                stack.add(dig to operand.toInt())
            } else {
                val (sibling, add) = stack.removeLast()
                val diff = add + sub
                if (diff < 0) {
                    digits[sibling] = -diff + 1 to 9
                    digits[dig] = 1 to 9 + diff
                } else {
                    digits[sibling] = 1 to 9 - diff
                    digits[dig] = 1 + diff to 9
                }
            }
            dig++
        }
    }
    return digits.toSortedMap().values.unzip().let { (a, b) ->
        b.joinToString("") to a.joinToString("")
    }
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
