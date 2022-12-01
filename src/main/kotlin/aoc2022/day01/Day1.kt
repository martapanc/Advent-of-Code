package aoc2022.day01


fun part1(inputList: List<String>): Int {
    val sums: MutableList<Int> = computeSums(inputList)
    return sums.max()
}

fun part2(inputList: List<String>): Int {
    val sums: MutableList<Int> = computeSums(inputList)

    var total = 0
    total += sums.max()
    sums.remove(sums.max())
    total += sums.max()
    sums.remove(sums.max())
    total += sums.max()
    return total
}

private fun computeSums(inputList: List<String>): MutableList<Int> {
    val sums: MutableList<Int> = mutableListOf()
    var agg = 0
    for (line: String in inputList) {
        if (line != "") {
            agg += Integer.parseInt(line)
        } else {
            sums.add(agg)
            agg = 0
        }
    }
    return sums
}
