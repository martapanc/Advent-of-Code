package aoc2018.day19

import aoc2018.day16.Operation.addi
import aoc2018.day16.Operation.addr
import aoc2018.day16.Operation.bani
import aoc2018.day16.Operation.banr
import aoc2018.day16.Operation.bori
import aoc2018.day16.Operation.borr
import aoc2018.day16.Operation.eqir
import aoc2018.day16.Operation.eqri
import aoc2018.day16.Operation.eqrr
import aoc2018.day16.Operation.gtir
import aoc2018.day16.Operation.gtri
import aoc2018.day16.Operation.gtrr
import aoc2018.day16.Operation.muli
import aoc2018.day16.Operation.mulr
import aoc2018.day16.Operation.seti
import aoc2018.day16.Operation.setr
import util.readInputLineByLine

fun readInputToInstructions(path: String): Pair<Int, List<Instruction>> {
    val input = readInputLineByLine(path)
    val instruction = input.subList(1, input.size)
        .map { it.split(" ") }
        .map { Instruction(it[0], it[1].toInt(), it[2].toInt(), it[3].toInt()) }
    val instructionPointer = input[0].replace("#ip ", "").toInt()
    return Pair(instructionPointer, instruction)
}

// Running part 2 (with reg[0] = 1) one of the registers gets soon populated with a big number (~ 10M).
// The program then will compute the sum of even divisors of such number
fun runProgram(input: Pair<Int, List<Instruction>>, isPart2: Boolean = false): Int {
    if (isPart2) {
        return 10551343.getFactors().sum()
    }
    val (instructionPointerIndex, instructions) = input
    var registers = mutableListOf(0, 0, 0, 0, 0, 0)
    var ip = registers[instructionPointerIndex]
    while (ip in instructions.indices) {
        val currentInstruction = instructions[ip]
        registers[instructionPointerIndex] = ip
        registers = currentInstruction.opcode!!(registers, currentInstruction.instructions).toMutableList()
        ip = registers[instructionPointerIndex] + 1
    }
    return registers[0]
}

data class Instruction(val opcodeString: String, val val1: Int, val val2: Int, val val3: Int) {
    private val opcodeMap = mapOf(
        "gtri" to ::gtri, "bani" to ::bani, "eqrr" to ::eqrr, "gtir" to ::gtir, "eqir" to ::eqir, "bori" to ::bori,
        "seti" to ::seti, "setr" to ::setr, "addr" to ::addr, "borr" to ::borr, "muli" to ::muli, "banr" to ::banr,
        "addi" to ::addi, "eqri" to ::eqri, "mulr" to ::mulr, "gtrr" to ::gtrr
    )
    val instructions = listOf(0, val1, val2, val3)
    val opcode = opcodeMap[opcodeString]
}

private fun Int.getFactors(): List<Int> =
    (1..this).mapNotNull { n ->
        if (this % n == 0) n else null
    }