package aoc2016.day18

fun computeTraps(input: String, rowCount: Int): Int {
    val rowsList = mutableListOf<String>()
    var currentRow = input
    rowsList.add(currentRow)

    var i = 1
    while (i < rowCount) {
        var newRow = ""

        for (k in currentRow.indices) {
            val left = if (k != 0) currentRow[k - 1] else '.'
            val center = currentRow[k]
            val right = if (k != currentRow.length - 1) currentRow[k + 1] else '.'
            newRow += when {
                left == '^' && center == '^' && right == '.' -> '^'
                left == '.' && center == '^' && right == '^' -> '^'
                left == '^' && center == '.' && right == '.' -> '^'
                left == '.' && center == '.' && right == '^' -> '^'
                else -> '.'
            }
        }
        currentRow = newRow
        rowsList.add(currentRow)
        i++
    }
    return rowsList.sumBy { row -> row.count { it == '.' } }
}