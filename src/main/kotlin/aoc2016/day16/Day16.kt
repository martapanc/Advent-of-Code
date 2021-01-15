package aoc2016.day16

fun expandString(input: String) : String {
    val b = input.reversed().replace("1", "2").replace("0", "1").replace("2", "0")
    return input + "0" + b
}

fun computeChecksum(input: String, diskSize: Int = 272): String {
    var expandedString = input
    while (expandedString.length < diskSize) {
        expandedString = expandString(expandedString)
    }
    return generateChecksum(expandedString.substring(0, diskSize))
}

tailrec fun generateChecksum(input: String) : String {
    val checkSum = (input.indices step 2).map { if (input[it] == input[it+1]) '1' else '0'}.joinToString("")
    if (checkSum.length % 2 == 1) return checkSum
    return generateChecksum(checkSum)
}