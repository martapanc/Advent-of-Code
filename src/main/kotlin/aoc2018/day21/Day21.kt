package aoc2018.day21

import aoc2018.day19.Instruction

fun runProgram(input: Pair<Int, List<Instruction>>, reg: Int = 2, inst: Int = 28): Sequence<Int> {
    return sequence {
        val (instructionPointerIndex, instructions) = input
        var registers = mutableListOf(0, 0, 0, 0, 0, 0)
        var ip = registers[instructionPointerIndex]
        val visited = LinkedHashSet<Int>()
        while (ip in instructions.indices) {
            registers[instructionPointerIndex] = ip
            val currentInstruction = instructions[ip]
            registers = currentInstruction.opcode!!(registers, currentInstruction.instructions).toMutableList()
            ip = registers[instructionPointerIndex] + 1
            if (ip == inst) {
                if (registers[reg] in visited) {
                    yield(visited.last())
                    return@sequence
                }
                visited += registers[reg]
                yield(registers[reg])
            }
        }
    }
}