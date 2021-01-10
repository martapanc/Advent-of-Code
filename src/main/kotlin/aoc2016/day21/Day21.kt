package aoc2016.day21

import util.readInputLineByLine

fun readInputToListAndCompute(path: String, input: String): String {
    var output = input
    for (line in readInputLineByLine(path)) {
        output = processInstruction(line, output)
    }
    return output
}

fun processInstruction(line: String, input: String): String {
    val swapPosition = Regex("swap position (\\d) with position (\\d)")
    val swapLetter = Regex("swap letter ([a-zA-Z]) with letter ([a-zA-Z])")
    val rotateLeftRight = Regex("rotate (left|right) (\\d) (step|steps)")
    val rotatePosition = Regex("rotate based on position of letter ([a-zA-Z])")
    val reverse = Regex("reverse positions (\\d) through (\\d)")
    val move = Regex("move position (\\d) to position (\\d)")
    var output = ""
    when {
        swapPosition.matches(line) -> output = runPositionMethod(swapPosition, line, input)
        swapLetter.matches(line) -> {
            val match = swapLetter.matchEntire(line)
            val letter1 = match!!.groupValues[1].single()
            val letter2 = match.groupValues[2].single()
            output = swapLetter(input, letter1, letter2)
        }
        rotateLeftRight.matches(line) -> {
            val match = rotateLeftRight.matchEntire(line)
            val rotation = Rotation.valueOf(match!!.groupValues[1].toUpperCase())
            val steps = match.groupValues[2].toInt()
            output = rotateLeftRight(input, rotation, steps)
        }
        rotatePosition.matches(line) -> {
            val match = rotatePosition.matchEntire(line)
            val letter = match!!.groupValues[1].single()
            val index = input.toList().indexOf(letter)
            output = rotateLeftRight(input, Rotation.RIGHT, if (index >= 4) index + 2 else index + 1)
        }
        reverse.matches(line) -> output = runPositionMethod(reverse, line, input)
        move.matches(line) -> output = runPositionMethod(move, line, input)
    }
    return output
}

private fun runPositionMethod(regex: Regex, line: String, input: String): String {
    val match = regex.matchEntire(line)
    val pos1 = match!!.groupValues[1].toInt()
    val pos2 = match.groupValues[2].toInt()
    val op = line.split(" ")[0].toUpperCase()
    return positionInstruction(pos1, pos2, input, Operation.valueOf(op))
}

fun positionInstruction(pos1: Int, pos2: Int, input: String, operation: Operation): String {
    var output = input
    val charArray = output.toCharArray().toMutableList()
    when (operation) {
        Operation.SWAP -> {
            val temp = output[pos1]
            charArray[pos1] = output[pos2]
            charArray[pos2] = temp
        }
        Operation.REVERSE -> {
            val selection = charArray.subList(pos1, pos2 + 1).reversed()
            charArray.removeAll(selection)
            charArray.addAll(pos1, selection)
        }
        Operation.MOVE -> {
            val removed = charArray.removeAt(pos1)
            charArray.add(pos2, removed)
        }
    }
    output = charArray.joinToString("")
    return output
}

fun swapLetter(input: String, letter1: Char, letter2: Char): String {
    val output = input.toMutableList()
    val index1 = output.indexOf(letter1)
    val index2 = output.indexOf(letter2)
    output[index1] = letter2
    output[index2] = letter1
    return output.joinToString("")
}

fun rotateLeftRight(input: String, rotation: Rotation, steps: Int): String {
    val output = input.toMutableList()
    repeat(steps) {
        if (rotation == Rotation.RIGHT) {
            output.add(0, output.removeAt(input.length - 1))
        } else {
            output.add(input.length - 1, output.removeAt(0))
        }
    }
    return output.joinToString("")
}

enum class Operation { SWAP, REVERSE, MOVE }
enum class Rotation { LEFT, RIGHT }
