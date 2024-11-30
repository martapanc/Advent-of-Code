package aoc2016.day09


fun decompressSequence(input: String, isPart2: Boolean = false): Long {
    var sequence = StringBuilder(input)
    var index = 0
    var accumulator = 0L
    while (index < sequence.length) {
        if (sequence[index] == '(') {
            val end = sequence.indexOf(')', index)
            val marker = sequence.substring(index + 1, end).split("x")
            val (charNo, times) = Pair(marker[0].toInt(), marker[1].toInt())
            val charsToRepeat = sequence.substring(end + 1, end + 1 + charNo).repeat(times)
            sequence.delete(end + 1, end + 1 + charNo)
            sequence.insert(end + 1, charsToRepeat)
            sequence.delete(index, end + 1)
            if (!isPart2)
                index += charsToRepeat.length
        } else {
            index = sequence.indexOf('(', index)
            if (index == -1) break
            if (sequence.length > 10000000) {
                accumulator += index
                sequence = StringBuilder(sequence.substring(index))
                index = 0
            }
        }
    }
    accumulator += sequence.length
    return accumulator
}

