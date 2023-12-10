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
    return 0
}

fun findNextNumInSequence(sequence: List<Long>): Long {
    val lastItem = reduce(sequence)
    return sequence[sequence.size - 1] + lastItem
}

fun reduce(sequence: List<Long>): Long {
    val reducedSequence = mutableListOf<Long>()
    (1 until sequence.size).forEach { i ->
        reducedSequence.add(sequence[i] - sequence[i - 1])
    }

    if (reducedSequence.all { it == 0L }) {
        return 0L
    } else {
        val lastItem = reduce(reducedSequence)
        return reducedSequence[reducedSequence.size - 1] + lastItem
    }
}