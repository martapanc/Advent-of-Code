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
        val currentCount = Operation.methods.count { it(sample.before, sample.instruction) == sample.after }
        if (currentCount >= 3) {
            matches3OrMoreOpcodesCount++
        }
    }
    return matches3OrMoreOpcodesCount
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
