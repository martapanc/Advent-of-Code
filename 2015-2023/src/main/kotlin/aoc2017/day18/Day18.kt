package aoc2017.day18

import util.readInputLineByLine
import java.util.concurrent.BlockingQueue
import java.util.concurrent.CompletableFuture
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

val charRegex = Regex("[a-z]")

fun runProgramPart1(path: String): Long {
    val instructions = readInputLineByLine(path)
    return DuetComputerPart1().runProgram(instructions)
}

fun runProgramPart2(path: String): Long {
    val instructions = readInputLineByLine(path)
    val computer1ReceivedQueue = LinkedBlockingQueue<Long>()
    val computer2ReceivedQueue = LinkedBlockingQueue<Long>()

    DuetComputerPart2(mutableMapOf('p' to 0L), computer1ReceivedQueue, computer2ReceivedQueue)
        .runProgram(instructions)

    return DuetComputerPart2(mutableMapOf('p' to 1L), computer2ReceivedQueue, computer1ReceivedQueue)
        .runProgram(instructions)
        .get()
}

private fun parseOrGetRegisterValue(
    value: String,
    registers: MutableMap<Char, Long>
) = if (charRegex.matches(value)) registers[value.first()] else value.toLong()

data class DuetComputerPart1(
    val registers: MutableMap<Char, Long> = mutableMapOf('p' to 0L),
    var index: Int = 0,
    var sound: Long = 0,
    var recoveredSound: Long = 0
) {
    fun runProgram(instructions: List<String>): Long {
        do {
            instructions.getOrNull(index)?.let { execute(it) }
        } while (index < instructions.size && recoveredSound == 0L)
        return recoveredSound
    }

    private fun execute(instruction: String) {
        val split = instruction.split(" ")
        val key = split[1].first()
        when (split[0]) {
            "snd" -> sound = registers[key]!!
            "set" -> registers[key] = parseOrGetRegisterValue(split[2], registers)!!
            "add" -> registers[key] = registers[key]!! + parseOrGetRegisterValue(split[2], registers)!!
            "mul" -> registers[key] = registers[key]!! * parseOrGetRegisterValue(split[2], registers)!!
            "mod" -> registers[key] = registers[key]!! % parseOrGetRegisterValue(split[2], registers)!!
            "rcv" -> if (registers[key] != 0L) recoveredSound = sound
            "jgz" -> {
                if (registers[key]!! > 0) {
                    val offset = (parseOrGetRegisterValue(split[2], registers)!!).toInt()
                    index += offset - 1
                }
            }
        }
        index++
    }
}

data class DuetComputerPart2(
    val registers: MutableMap<Char, Long> = mutableMapOf(),
    val send: BlockingQueue<Long>,
    val receive: BlockingQueue<Long>,
    var index: Int = 0,
    var sent: Long = 0
) {
    fun runProgram(instructions: List<String>): CompletableFuture<Long> {
        return CompletableFuture.supplyAsync {
            do {
                instructions.getOrNull(index)?.let { execute(it) }
            } while (index in instructions.indices)
            sent
        }
    }

    private fun execute(instruction: String) {
        val split = instruction.split(" ")
        val key = split[1].first()
        when (split[0]) {
            "snd" -> {
                send.put(registers[key]!!)
                sent++
            }
            "set" -> registers[key] = parseOrGetRegisterValue(split[2], registers)!!
            "add" -> registers[key] = registers[key]!! + parseOrGetRegisterValue(split[2], registers)!!
            "mul" -> registers[key] = registers[key]!! * parseOrGetRegisterValue(split[2], registers)!!
            "mod" -> registers[key] = registers[key]!! % parseOrGetRegisterValue(split[2], registers)!!
            "rcv" -> {
                try {
                    registers[key] = receive.poll(1, TimeUnit.SECONDS)!!
                } catch (e: Exception) {
                    println(e)
                    index = -2
                }
            }
            "jgz" -> {
                if (parseOrGetRegisterValue(split[1], registers)!! > 0) {
                    val offset = (parseOrGetRegisterValue(split[2], registers)!!).toInt()
                    index += offset - 1
                }
            }
        }
        index++
    }
}

fun readInputAndRunInstructions(path: String): Long {
    val lastFrequencyPlayed = ('a'..'b').map { it to 0L }.toMap().toMutableMap()
    var lastKey = '0'
    val registers = ('a'..'p').map { it to 0L }.toMap().toMutableMap()
    var index = 0
    val instructions = readInputLineByLine(path)
    loop@ while (index < instructions.size) {
        val split = instructions[index].split(" ")
        val key = split[1].first()
        when (split[0]) {
            "snd" -> lastFrequencyPlayed[key] = registers[key]!!.toLong()
            "set" -> registers[key] = parseOrGetRegisterValue(split[2], registers)!!
            "add" -> registers[key] = registers[key]!! + parseOrGetRegisterValue(split[2], registers)!!
            "mul" -> registers[key] = registers[key]!! * parseOrGetRegisterValue(split[2], registers)!!
            "mod" -> registers[key] = registers[key]!! % parseOrGetRegisterValue(split[2], registers)!!
            "rcv" -> {
                if (registers[key] != 0L) {
                    lastKey = if (lastFrequencyPlayed[key] != 0L) key else 'b'
                    break@loop
                }
            }
            "jgz" -> {
                if (registers[key]!! > 0) {
                    val offset = (parseOrGetRegisterValue(split[2], registers)!! % instructions.size).toInt()
                    index += offset - 1
                }
            }
        }
        index++
    }
    return lastFrequencyPlayed[lastKey]!!
}
