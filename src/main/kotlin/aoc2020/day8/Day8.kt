package aoc2020.day8

import java.io.File
import java.io.InputStream

fun readInputToMap(path: String): Map<Int, GameData> {
    val inputStream: InputStream = File(path).inputStream()
    val lineList = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val inputMap = mutableMapOf<Int, GameData>()
    for ((i, line) in lineList.withIndex()) {
       val data = line.split(" ")
        inputMap[i] = GameData(data[0], data[1].toInt(), false)
    }
    return inputMap
}

fun playTheGame(inputMap: Map<Int, GameData>): GameOver {
    var accumulator = 0
    var index = 0
    while (true) {
        val current: GameData? = inputMap[index]
        if (current == null || current.executed) { break }
        when (current.command) {
            "nop" -> { index++ }
            "acc" -> {
                accumulator += current.amount
                index++
            }
            "jmp" -> { index += current.amount }
        }
        current.executed = true
    }
    return GameOver(accumulator, index >= inputMap.size)
}

fun fixInstructionAndPlay(map: Map<Int, GameData>): Int {
    for (entry in map.entries) {
        if (entry.value.command == "acc") { continue }
        val gameOver = playTheGame(createMapCopyWithFixedInstruction(map, entry))
        if (gameOver.terminated) {
            return gameOver.accumulator
        }
    }
    return -1
}

private fun createMapCopyWithFixedInstruction(map: Map<Int, GameData>, entry: Map.Entry<Int, GameData>): HashMap<Int, GameData> {
    val copyInputMap = HashMap(map)
    for (copyEntry in copyInputMap.values) { copyEntry.executed = false }
    val newCommand: String = if (entry.value.command == "nop") { "jmp" } else { "nop" }
    copyInputMap[entry.key] = GameData(newCommand, entry.value.amount, false)
    return copyInputMap
}

data class GameData(var command: String, var amount: Int, var executed: Boolean)

data class GameOver(var accumulator: Int, var terminated: Boolean)