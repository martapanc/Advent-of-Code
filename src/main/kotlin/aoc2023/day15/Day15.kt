package aoc2023.day15

fun part1(lines: List<String>): Int {
    val sequences = split(lines)
    return sequences.sumOf { hash(it) }
}

fun part2(lines: List<String>): Long {
    val sequences = split(lines).map { Sequence(it) }
//    val boxes = mutableMapOf<Int,
    return 0
}

fun hash(inputString: String): Int {
    var currentValue = 0
    for (char in inputString) {
        currentValue += char.code
        currentValue *= 17
        currentValue %= 256
    }
    return currentValue
}

private fun split(lines: List<String>) = lines[0].split(",")

data class Lens(val label: String, val focalLength: Int)

data class Sequence(val seqString: String) {
    val label: String by lazy { parseLabel() }
    val box: Int by lazy { calculateHash() }
    val opType: OpType by lazy { parseOpType() }
    val focalLength: Int? by lazy { parseFocalLength() }

    private fun parseLabel(): String = replaceAndSplit()[0]

    private fun parseOpType(): OpType {
        if (seqString.contains("-")) {
            return OpType.DASH
        }
        return OpType.EQUALS
    }

    private fun calculateHash(): Int = hash(label)

    private fun parseFocalLength(): Int? {
        if (seqString.contains("="))
            return replaceAndSplit()[1].toInt()
        return null
    }

    private fun replaceAndSplit() = seqString
        .replace("-", " ")
        .replace("=", " ")
        .split(" ")

    override fun toString(): String {
        return "{label: '$label', box: $box, opType: $opType, focalLength: $focalLength}"
    }
}

enum class OpType {
    DASH, EQUALS
}
