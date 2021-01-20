package aoc2017.day18

import aoc2019.day22.modulo
import util.readInputLineByLine

val charRegex = Regex("[a-z]")

fun readInputAndRunInstructions(path: String): Long {
    val lastFrequencyPlayed = ('a'..'b').map { it to 0L }.toMap().toMutableMap()
    var lastKey = '0'
    val registers = ('a'..'z').map { it to 0L }.toMap().toMutableMap()
    var index = 0
    val instructions = readInputLineByLine(path)
    loop@ while (index < instructions.size) {
        val split = instructions[index].split(" ")
        val key = split[1].first()
        when (split[0]) {
            "snd" -> lastFrequencyPlayed[key] = registers[key]!!.toLong()
            "set" -> registers[key] = readOrGetRegisterValue(split, registers)!!
            "add" -> registers[key] = registers[key]!! + readOrGetRegisterValue(split, registers)!!
            "mul" -> {
                registers[key] = registers[key]!! * readOrGetRegisterValue(split, registers)!!
            }
            "mod" -> {
                registers[key] = registers[key]!! % readOrGetRegisterValue(split, registers)!!
            }
            "rcv" -> {
                if (registers[key] != 0L) {
                    lastKey = if (lastFrequencyPlayed[key] != 0L) key else 'b'
                    break@loop
                }
            }
            "jgz" -> {
                if (registers[key]!! > 0) {
                    val offset = (readOrGetRegisterValue(split, registers)!! % instructions.size).toInt()
                    index += offset - 1
                }
            }
        }
        index++
    }

    return lastFrequencyPlayed[lastKey]!!
}

private fun readOrGetRegisterValue(
    split: List<String>,
    registers: MutableMap<Char, Long>
) = if (charRegex.matches(split[2])) registers[split[2].first()] else split[2].toLong()