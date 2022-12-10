package aoc2022.day10

fun solve(commands: List<String>): Int {
    var crtRow = ""
    var crtRowIndex = 0
    var doubleCycleStarted = false
    var index = 0
    var signalStrength = 0
    var xRegister = 1
    var spriteIndices = updateSprite(xRegister)

    (1 .. 240).forEach { cycle ->
        // Part 1
        if (cycle in arrayOf(20, 60, 100, 140, 180, 220)) {
            signalStrength += xRegister * cycle
        }
        // Part 2
        crtRow += if (crtRowIndex++ in spriteIndices) "#" else "."

        if (cycle in arrayOf(40, 80, 120, 160, 200, 240)) {
            println(crtRow)
            crtRow = ""
            crtRowIndex = 0
        }

        when {
            doubleCycleStarted -> {
                xRegister +=  commands[index].split(" ")[1].toInt()
                spriteIndices = updateSprite(xRegister)
                index++
                doubleCycleStarted = false
            }
            commands[index].startsWith("noop") -> index++
            else -> doubleCycleStarted = true
        }
    }
    println()
    return signalStrength
}

private fun updateSprite(xReg: Int) = arrayOf(xReg - 1, xReg, xReg + 1)
