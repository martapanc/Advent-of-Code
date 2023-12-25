package aoc2023.day15

fun part1(lines: List<String>): Int {
    val sequences = split(lines)
    return sequences.sumOf { hash(it) }
}

fun part2(lines: List<String>): Long {
    val sequences = split(lines).map { Sequence(it) }
    val boxes = mutableMapOf<Int, MutableList<Lens>>()
    for (i in 0 until 256) {
        boxes[i] = mutableListOf()
    }

    sequences.forEach { seq ->
        if (seq.opType == OpType.EQUALS) {
            val lens = Lens(seq.label, seq.focalLength!!)
            val existingLensWithLabel = boxes[seq.box]!!.filter { it.label == seq.label }
            if (existingLensWithLabel.isNotEmpty()) {
                val index = boxes[seq.box]!!.indexOf(existingLensWithLabel[0])
                boxes[seq.box]!!.removeAt(index)
                boxes[seq.box]!!.add(index, lens)
            } else {
                boxes[seq.box]!!.add(lens)
            }
        } else {
            val existingLensWithLabel = boxes[seq.box]!!.filter { it.label == seq.label }
            if (existingLensWithLabel.isNotEmpty()) {
                boxes[seq.box]!!.remove(existingLensWithLabel[0])
            }
        }
    }
    var sum = 0L
    boxes.entries.forEachIndexed { b, box ->
        box.value.forEachIndexed { l, lens ->
            sum += (b + 1) * (l + 1) * lens.focalLength
        }
    }
    return sum
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
    val focalLength: Int? by lazy { parseFocalLength() }
    val opType: OpType by lazy { parseOpType() }
    val box: Int by lazy { calculateHash() }

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
