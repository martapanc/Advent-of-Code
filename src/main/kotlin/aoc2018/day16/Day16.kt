package aoc2018.day16

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
import kotlin.reflect.jvm.javaMethod

fun readInputPart1(path: String): List<Sample> {
    val sampleList = mutableListOf<Sample>()
    val input = readInputLineByLine(path)
    for (i in input.indices step 4) {
        val before = input[i].replace("Before: [", "").replace("]", "").split(", ").map { it.toInt() }
        val instruction = input[i + 1].split(" ").map { it.toInt() }
        val after = input[i + 2].replace("After:  [", "").replace("]", "").split(", ").map { it.toInt() }
        sampleList.add(Sample(before, instruction, after))
    }
    return sampleList
}

fun readInputPart2(path: String): List<List<Int>> {
    return readInputLineByLine(path).map { line -> line.split(" ").map { it.toInt() } }
}

fun countSamplesMatchingOpcodes(samples: List<Sample>): Int {
    val opcodeMatchMap = mutableMapOf<Sample, List<String>>()
    for (sample in samples) {
        opcodeMatchMap[sample] = Operation.methods
            .filter { it(sample.before, sample.instruction) == sample.after }
            .map {
                it.javaMethod.toString()
                    .replace("public final java.util.List aoc2018.day16.Operation.", "")
                    .replace("(java.util.List,java.util.List)", "")
            }
    }
    return opcodeMatchMap.values.count { it.size >= 3 }
}

fun runProgram(instructions: List<List<Int>>): Int {
    val opcodeMap = mapOf(
        0 to ::gtri, 1 to ::bani, 2 to ::eqrr, 3 to ::gtir, 4 to ::eqir, 5 to ::bori, 6 to ::seti, 7 to ::setr,
        8 to ::addr, 9 to ::borr, 10 to ::muli, 11 to ::banr, 12 to ::addi, 13 to ::eqri, 14 to ::mulr, 15 to ::gtrr
    )
    var registers = listOf(0, 0, 0, 0)
    for (instruction in instructions) {
        val method = opcodeMap[instruction[0]]
        registers = method!!(registers, instruction)
    }
    return registers[0]
}

private object Operation {
    val methods = listOf(
        ::addr, ::addi, ::mulr, ::muli, ::banr, ::bani, ::borr, ::bori, ::setr, ::seti, ::gtir, ::gtri, ::gtrr,
        ::eqir, ::eqri, ::eqrr
    )

    fun addr(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = reg[instruction[1]] + reg[instruction[2]] }

    fun addi(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = reg[instruction[1]] + instruction[2] }

    fun mulr(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = reg[instruction[1]] * reg[instruction[2]] }

    fun muli(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = reg[instruction[1]] * instruction[2] }

    fun banr(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = reg[instruction[1]] and reg[instruction[2]] }

    fun bani(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = reg[instruction[1]] and instruction[2] }

    fun borr(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = reg[instruction[1]] or reg[instruction[2]] }

    fun bori(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = reg[instruction[1]] or instruction[2] }

    fun setr(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = reg[instruction[1]] }

    fun seti(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = instruction[1] }

    fun gtir(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = if (instruction[1] > reg[instruction[2]]) 1 else 0 }

    fun gtri(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = if (reg[instruction[1]] > instruction[2]) 1 else 0 }

    fun gtrr(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = if (reg[instruction[1]] > reg[instruction[2]]) 1 else 0 }

    fun eqir(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = if (instruction[1] == reg[instruction[2]]) 1 else 0 }

    fun eqri(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = if (reg[instruction[1]] == instruction[2]) 1 else 0 }

    fun eqrr(reg: List<Int>, instruction: List<Int>): List<Int> =
        reg.toMutableList().apply { this[instruction[3]] = if (reg[instruction[1]] == reg[instruction[2]]) 1 else 0 }
}

data class Sample(val before: List<Int>, val instruction: List<Int>, val after: List<Int>)
