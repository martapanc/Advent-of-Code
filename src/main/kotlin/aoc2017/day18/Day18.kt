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
        when (split[0]) {
            "snd" -> {
                val key = split[1].first()
                lastFrequencyPlayed[key] = registers[key]!!.toLong()
                index++
            }
            "set" -> {
                val value = if (charRegex.matches(split[2])) registers[split[2].first()] else split[2].toLong()
                registers[split[1].first()] = value!!
                index++
            }
            "add" -> {
                val value = if (charRegex.matches(split[2])) registers[split[2].first()] else split[2].toLong()
                val key = split[1].first()
                registers[key] = registers[key]!!.plus(value!!)
                index++
            }
            "mul" -> {
                val value = if (charRegex.matches(split[2])) registers[split[2].first()] else split[2].toLong()
                val key = split[1].first()
                registers[key] = registers[key]!!.times(value!!)
                index++
            }
            "mod" -> {
                val value = if (charRegex.matches(split[2])) registers[split[2].first()] else split[2].toLong()
                val key = split[1].first()
                registers[key] = registers[key]!!.modulo(value!!)
                index++
            }
            "rcv" -> {
                val key = split[1].first()
                if (registers[key] != 0L) {
                    lastKey = if (lastFrequencyPlayed[key] != 0L) key else 'b'
                    break@loop
                }
                else index++
            }
            "jgz" -> {
                val key = split[1].first()
                if (registers[key]!! > 0) {
                    val value = if (charRegex.matches(split[2])) registers[split[2].first()] else split[2].toLong()
                    val offset = (value!! % instructions.size).toInt()
                    index += offset
                } else index++
            }
        }
    }

    return lastFrequencyPlayed[lastKey]!!
}