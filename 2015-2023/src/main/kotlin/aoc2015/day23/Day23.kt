package aoc2015.day23

import util.readInputLineByLine

fun readInputToList(path: String, isPart2: Boolean = false): Long? {
    val instructions = readInputLineByLine(path)
    val registers = mutableMapOf('a' to if (isPart2) 1L else 0L, 'b' to 0L)
    var index = 0
    while (index in instructions.indices) {
        val split = instructions[index].split(" ")
        if (split.size == 2) {
            val regKey = split[1].first()
            when (split[0]) {
                "hlf" -> registers[regKey] = registers[regKey]!! / 2
                "tpl" -> registers[regKey] = registers[regKey]!! * 3
                "inc" -> registers[regKey] = registers[regKey]!! + 1
                "jmp" -> index += split[1].toInt() - 1
            }
        } else {
            val regKey = split[1].first()
            val offset = split[2].toInt()
            when (split[0]) {
                "jio" -> if (registers[regKey]!! == 1L) {
                    index += offset - 1
                }
                "jie" -> if (registers[regKey]!! % 2 == 0L) {
                    index += offset - 1
                }
            }
        }
        index++
    }
    return registers['b']
}