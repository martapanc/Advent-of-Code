package aoc2022.day10

fun part1(list: List<String>): Int {
    var signalStrength = 0
    var xRegister = 1
    var index = 0
    var doubleCircleStarted = false
    (1 .. 220).forEach { cycle ->
        if (cycle in arrayOf(20, 60, 100, 140, 180, 220)) {
            signalStrength += xRegister * cycle
        }
        val line = list[index]
        if (doubleCircleStarted) {
            xRegister += line.split(" ")[1].toInt()
            index++
            doubleCircleStarted = false
        } else if (line.startsWith("noop")) {
            index++
        } else {
            doubleCircleStarted = true
        }
    }
    return signalStrength
}

fun part2(list: List<String>) {
    var crtRow = ""
    var crtRowIndex = 0
    var xReg = 1
    var spriteIndices = updateSprite(xReg)
    var index = 0
    var doubleCircleStarted = false

    (1 .. 240).forEach { cycle ->
        crtRow += if (crtRowIndex++ in spriteIndices) "#" else "."

        if (cycle in arrayOf(40, 80, 120, 160, 200, 240)) {
            println(crtRow)
            crtRow = ""
            crtRowIndex = 0
        }

        val line = list[index]
        if (doubleCircleStarted) {
            xReg += line.split(" ")[1].toInt()
            spriteIndices = updateSprite(xReg)
            index++
            doubleCircleStarted = false
        } else if (line.startsWith("noop")) {
            index++
        } else {
            doubleCircleStarted = true
        }
    }
    println()
}

private fun updateSprite(xReg: Int) = arrayOf(xReg - 1, xReg, xReg + 1)
