package aoc2017.day15

fun computeNextNumber(number: Long, generator: Generator): Long {
    val factor = if (generator == Generator.A) 16807 else 48271
    return number * factor % 2147483647
}

fun genRightmost16BinRepresentation(number: Long): String {
    // Find num % 2^16 to allow Long processing and obtain the same result
    var binaryString = Integer.toBinaryString((number % 65536).toInt())
    while (binaryString.length < 16) {
        binaryString = "0$binaryString"
    }
    return binaryString.substring(binaryString.length - 16, binaryString.length)
}

fun compareGeneratorValues(generatorAStart: Long, generatorBStart: Long): Long {
    var count = 0L
    var generatorAValue = generatorAStart
    var generatorBValue = generatorBStart
    repeat(40_000_000) {
        generatorAValue = computeNextNumber(generatorAValue, Generator.A)
        generatorBValue = computeNextNumber(generatorBValue, Generator.B)
        if (genRightmost16BinRepresentation(generatorAValue) == genRightmost16BinRepresentation(generatorBValue)) {
            count++
        }
    }
    return count
}

fun compareGeneratorValuesPart2(generatorAStart: Long, generatorBStart: Long): Long {
    var count = 0L
    var generatorAValue = generatorAStart
    var generatorBValue = generatorBStart
    for (i in 1 until 5_000_000) {
        if (i == 1055) {
            println()
        }
        generatorAValue = computeNextNumber(generatorAValue, Generator.A)
        while (generatorAValue % 4 != 0L) {
            generatorAValue = computeNextNumber(generatorAValue, Generator.A)
        }
        generatorBValue = computeNextNumber(generatorBValue, Generator.B)
        while (generatorBValue % 8 != 0L) {
            generatorBValue = computeNextNumber(generatorBValue, Generator.B)
        }
        if (genRightmost16BinRepresentation(generatorAValue) == genRightmost16BinRepresentation(generatorBValue)) {
            count++
        }
    }
    return count
}

enum class Generator { A, B }
