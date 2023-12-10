package aoc2023.day09

fun parse(lines: List<String>): List<List<Long>> {
    val sequences = mutableListOf<List<Long>>()
    lines.forEach { line ->
        sequences.add(line.split(" ").map { it.toLong() })
    }
    return sequences
}

fun part1(sequences: List<List<Long>>): Long {
    var sum = 0L
    sequences.forEach { sequence ->
        sum += findNextNumInSequence(sequence)
    }
    return sum
}

fun part2(sequences: List<List<Long>>): Long {
    var sum = 0L
    sequences.forEach { sequence ->
        sum += findPreviousNumInSequence(sequence)
    }
    return sum
}

fun findNextNumInSequence(sequence: List<Long>): Long {
    val lastItem = reduceToNext(sequence)
    return sequence[sequence.size - 1] + lastItem
}

fun findPreviousNumInSequence(sequence: List<Long>): Long {
    val firstItem = reduceToPrevious(sequence)
    return sequence[0] - firstItem
}

fun reduceToNext(sequence: List<Long>): Long {
    val reducedSequence = computeDiffSequence(sequence)

    if (reducedSequence.all { it == 0L }) {
        return 0L
    } else {
        val lastItem = reduceToNext(reducedSequence)
        return reducedSequence[reducedSequence.size - 1] + lastItem
    }
}

fun reduceToPrevious(sequence: List<Long>): Long {
    val reducedSequence = computeDiffSequence(sequence)

    if (reducedSequence.all { it == 0L }) {
        return 0L
    } else {
        val firstItem = reduceToPrevious(reducedSequence)
        return reducedSequence[0] - firstItem
    }
}

private fun computeDiffSequence(sequence: List<Long>): MutableList<Long> {
    val reducedSequence = mutableListOf<Long>()
    (1 until sequence.size).forEach { i ->
        reducedSequence.add(sequence[i] - sequence[i - 1])
    }
    return reducedSequence
}