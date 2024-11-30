package aoc2017.day23

import aoc2017.day18.charRegex
import util.readInputLineByLine

fun readInputAndRun(path: String): Int {
    val instructions = readInputLineByLine(path)
    val registers = ('a'..'h').map { it to 0L }.toMap().toMutableMap()
    var mulCount = 0
    var index = 0
    while (index in instructions.indices) {
        val split = instructions[index].split(" ")
        val regKey = split[1].first()
        val value = parseOrGetRegisterValue(split[2], registers)!!
        when (split[0]) {
            "set" -> registers[regKey] = value
            "sub" -> registers[regKey] = registers[regKey]!! - value
            "mul" -> {
                registers[regKey] = registers[regKey]!! * value
                mulCount++
            }
            "jnz" -> if (registers[regKey] != 0L) {
                index += value.toInt() - 1
            }
        }
        index++
    }
    return mulCount
}

// starting at b, how many of the next 1,000 numbers, skipping by 17, are NOT prime?
fun readInputAndRunPart2(path: String): Int {
    val b = readInputLineByLine(path).first().split(" ")[2].toInt() * 100 + 100000
    return (b..b + 17000 step 17).count {
        !it.toBigInteger().isProbablePrime(2)
    }
}

private fun parseOrGetRegisterValue(value: String, registers: MutableMap<Char, Long>) =
    if (charRegex.matches(value)) registers[value.first()] else value.toLong()