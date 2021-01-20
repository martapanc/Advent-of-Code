package aoc2019.day21

import aoc2019.commons.IntCodeProgram
import util.readInputLineByLine

// @
// #####  => No need to jump.
//  ABCD
//
// @
// # ###  => Need to jump, or the droid will fall
//  ABCD
//
// @
// ##  #  => Don't need to jump, even if doing so does not hurt.
//  ABCD
//
// @
// ## #   => Jumping would make the droid fall, don't jump.
//  ABCD
//
// J = !(A & B & C) & D
//
// NOT A J      J = !A
// NOT J J      J = A
// AND B J      J = A & B
// AND C J      J = A & B & C
// NOT J J      J = !(A & B & C)
// AND D J      J = !(A & B & C) & D

fun readIntcodeInput(path: String, springScript: String): Long {
    val intcode = readInputLineByLine(path)[0].split(",").map { it.toLong() }
    val program = IntCodeProgram(intcode)
    program.inputAscii(springScript)
    program.execute()
    return program.output.last()
}