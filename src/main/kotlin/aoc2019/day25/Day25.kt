package aoc2019.day25

import aoc2019.commons.IntCodeProgram
import util.readInputLineByLine

fun readIntcodeInputAndRun(path: String) {
    val intcode = readInputLineByLine(path)[0].split(",").map { it.toLong() }
    val program = IntCodeProgram(intcode)

    val inputValues = listOf(
        "south\n",
//        "take weather machine\n",
        "north\n",
        "west\n",
        "west\n",
        "north\n",
        "take space heater\n",
        "south\n",
        "east\n",
        "south\n",
//        "take festive hat\n",
        "east\n",
//        "take whirled peas\n",
        "west\n",
        "south\n",
        "take sand\n",
        "north\n",
        "north\n",
        "east\n",
        "east\n",
        "take mug\n",
        "east\n",
        "south\n",
        "east\n",
        "south\n",
        "take easter egg\n",
        "north\n",
        "west\n",
        "west\n",
        "south\n",
        "west\n",
//        "take shell\n",
        "south\n",
        "south\n"
    )
    program.inputAscii(inputValues)
    program.execute()
//    println(program.outputToAscii())
}