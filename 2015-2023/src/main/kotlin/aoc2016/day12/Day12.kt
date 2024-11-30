package aoc2016.day12

import util.readInputLineByLine

val registerRegex = Regex("[a-d]")
val numberRegex = Regex("-?[0-9]+")

fun readInputAndRun(path: String, isPart2: Boolean = false): Int {
    val registers = mutableMapOf('a' to 0, 'b' to 0, 'c' to if (isPart2) 1 else 0, 'd' to 0)
    val instructions = readInputLineByLine(path)
    var i = 0
    while (i < instructions.size) {
        val split = instructions[i].split(" ")
        when (split[0]) {
            "cpy" -> {
                val targetRegId = split[2].first()
                if (registerRegex.matches(split[1])) {
                    registers[targetRegId] = registers[split[1].first()]!!
                } else if (numberRegex.matches(split[1])) {
                    registers[targetRegId] = split[1].toInt()
                }
                i++
            }
            "jnz" -> {
                if (registerRegex.matches(split[1])) {
                    if (registers[split[1].first()] != 0) {
                        i += split[2].toInt()
                    } else i++
                } else {
                    if (split[1].toInt() != 0) {
                        i += split[2].toInt()
                    } else i++
                }
            }
            "inc" -> {
                registers[split[1].first()] = registers[split[1].first()]!!.plus(1)
                i++
            }
            "dec" -> {
                registers[split[1].first()] = registers[split[1].first()]!!.minus(1)
                i++
            }
        }
    }
    return registers['a']!!
}