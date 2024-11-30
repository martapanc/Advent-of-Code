package aoc2017.day16

import aoc2016.day21.Rotation
import aoc2016.day21.rotateLeftRight
import util.readInputLineByLine

fun readInputToListAndCompute(path: String, start: String, repeat: Long = 1): String {
    var output = start
    val split = readInputLineByLine(path)[0].split(",")
    // The progression repeats every 44 runs
    for (i in 1..repeat % 44) {
        for (instruction in split) {
            output = processInstruction(instruction, output)
        }
    }
    return output
}

fun processInstruction(instruction: String, input: String): String {
    val spin = Regex("s(\\d+)")
    val exchange = Regex("x(\\d+)/(\\d+)")
    val partner = Regex("p([a-z])/([a-z])")
    var output = ""
    when {
        spin.matches(instruction) -> {
            val match = spin.matchEntire(instruction)
            output = runSpinMethod(input, match!!.groupValues[1].toInt())
        }
        exchange.matches(instruction) -> {
            val match = exchange.matchEntire(instruction)
            output = runExchangeMethod(input, match!!.groupValues[1].toInt(), match.groupValues[2].toInt())
        }
        partner.matches(instruction) -> {
            val match = partner.matchEntire(instruction)
            output = runPartnerMethod(input, match!!.groupValues[1].first(), match.groupValues[2].first())
        }
    }
    return output
}

fun runSpinMethod(input: String, spinValue: Int): String {
    return rotateLeftRight(input, Rotation.RIGHT, spinValue)
}

fun runExchangeMethod(input: String, pos1: Int, pos2: Int): String {
    val output = input.toMutableList()
    val char1 = output[pos1]
    output[pos1] = output[pos2]
    output[pos2] = char1
    return output.joinToString("")
}

fun runPartnerMethod(input: String, char1: Char, char2: Char): String {
    val output = input.toMutableList()
    val index1 = output.indexOf(char1)
    val index2 = output.indexOf(char2)
    output[index1] = char2
    output[index2] = char1
    return output.joinToString("")
}
