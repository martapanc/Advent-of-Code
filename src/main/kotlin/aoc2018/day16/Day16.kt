package aoc2018.day16

import util.readInputLineByLine

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

fun countSamplesMatchingOpcodes(samples: List<Sample>): Int {
    var matches3OrMoreOpcodesCount = 0
    for (sample in samples) {
        var currentCount = 0
        for (method in sample.methods) {

        }
    }
    return matches3OrMoreOpcodesCount
}

data class Sample(val before: List<Int>, val instruction: List<Int>, val after: List<Int>) {

    val methods = listOf(
        ::addr, ::addi, ::mulr, ::muli, ::banr, ::bani, ::borr, ::bori, ::setr, ::seti, ::gtir, ::gtri, ::gtrr,
        ::eqir, ::eqri, ::eqrr
    )

    fun addr(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = outputRegisters[instruction[1]] + outputRegisters[instruction[2]]
        return outputRegisters
    }

    fun addi(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = outputRegisters[instruction[1]] + instruction[2]
        return outputRegisters
    }

    fun mulr(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = outputRegisters[instruction[1]] * outputRegisters[instruction[2]]
        return outputRegisters
    }

    fun muli(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = outputRegisters[instruction[1]] * instruction[2]
        return outputRegisters
    }

    fun banr(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = outputRegisters[instruction[1]] and outputRegisters[instruction[2]]
        return outputRegisters
    }

    fun bani(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = outputRegisters[instruction[1]] and instruction[2]
        return outputRegisters
    }

    fun borr(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = outputRegisters[instruction[1]] or outputRegisters[instruction[2]]
        return outputRegisters
    }

    fun bori(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = outputRegisters[instruction[1]] or instruction[2]
        return outputRegisters
    }

    fun setr(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = outputRegisters[instruction[1]]
        return outputRegisters
    }

    fun seti(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = instruction[1]
        return outputRegisters
    }

    fun gtir(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = if (instruction[1] > outputRegisters[instruction[2]]) 1 else 0
        return outputRegisters
    }

    fun gtri(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = if (outputRegisters[instruction[1]] > instruction[2]) 1 else 0
        return outputRegisters
    }

    fun gtrr(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] =
            if (outputRegisters[instruction[1]] > outputRegisters[instruction[2]]) 1 else 0
        return outputRegisters
    }

    fun eqir(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = if (instruction[1] == outputRegisters[instruction[2]]) 1 else 0
        return outputRegisters
    }

    fun eqri(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = if (outputRegisters[instruction[1]] == instruction[2]) 1 else 0
        return outputRegisters
    }

    fun eqrr(registers: List<Int>, instruction: List<Int>): List<Int> {
        val outputRegisters = registers.toMutableList()
        outputRegisters[instruction[3]] = if (outputRegisters[instruction[1]] == outputRegisters[instruction[2]]) 1 else 0
        return outputRegisters
    }
}
