package aoc2018.day11

fun computePowerLevelOfCell(x: Int, y: Int, GRID_SN: Int): Int {
    val rackID = x + 10
    var powerLevel = rackID * y
    powerLevel += GRID_SN
    powerLevel *= rackID
    powerLevel = powerLevel / 100 % 10
    return powerLevel - 5
}

fun createMatrix(GRID_SN: Int) {
    val matrix = Array(300) { IntArray(300) }
    for (y in 0..299) {
        for (x in 0..299) {
            matrix[y][x] = computePowerLevelOfCell(x + 1, y + 1, GRID_SN)
        }
    }
    for (y in 0..299) {
        for (x in 0..299) {
            if (matrix[y][x] >= 0) print(" " + matrix[y][x] + "\t") else print(matrix[y][x].toString() + "\t")
        }
        println()
    }
    println()
}

fun find3x3CellWithLargestPower(GRID_SN: Int): IntArray {
    val maxPowerCell = intArrayOf(0, 0, 0)
    for (y in 0..297) {
        for (x in 0..297) {
            val totalPower = find3x3CellPower(x, y, GRID_SN)
            if (totalPower > maxPowerCell[2]) {
                maxPowerCell[0] = x
                maxPowerCell[1] = y
                maxPowerCell[2] = totalPower
            }
        }
    }
    return maxPowerCell
}

fun find_KxK_CellWithLargestPower(GRID_SN: Int, kMin: Int, kMax: Int): IntArray {
    val maxPowerCell = intArrayOf(0, 0, -10000, 0)
    for (k in kMin..kMax) {
        for (y in 0 until 301 - k) {
            for (x in 0 until 301 - k) {
                val totalPower = findKxKCellPower(x, y, k, GRID_SN)
                if (totalPower > maxPowerCell[2]) {
                    maxPowerCell[0] = x
                    maxPowerCell[1] = y
                    maxPowerCell[2] = totalPower
                    maxPowerCell[3] = k
                }
            }
        }
    }
    return maxPowerCell
}

fun find3x3CellPower(topLeftX: Int, topLeftY: Int, GRID_SN: Int): Int {
    var power = 0
    for (y in topLeftY until topLeftY + 3) {
        for (x in topLeftX until topLeftX + 3) {
            power += computePowerLevelOfCell(x, y, GRID_SN)
        }
    }
    return power
}

fun findKxKCellPower(topLeftX: Int, topLeftY: Int, k: Int, GRID_SN: Int): Int {
    var power = 0
    for (y in topLeftY until topLeftY + k) {
        for (x in topLeftX until topLeftX + k) {
            power += computePowerLevelOfCell(x, y, GRID_SN)
        }
    }
    return power
}
