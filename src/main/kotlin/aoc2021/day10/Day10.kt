package aoc2021.day10

fun findSyntaxErrorChecksum(inputList: List<String>): Int {
  var checksum = 0
  val valueMap = mutableMapOf(')' to 3, ']' to 57, '}' to  1197, '>' to 25137)

  inputList.forEach { line ->
    var tempLine = line
    do {
      val length = tempLine.length
      val reducedLine = tempLine
        .replace("()", "")
        .replace("[]", "")
        .replace("{}", "")
        .replace("<>", "")
      tempLine = reducedLine
    } while (length != reducedLine.length)

    val firstSyntaxError = tempLine.find { it == ')' || it == ']' || it == '}' || it == '>'}

    if (firstSyntaxError != null) {
      checksum += valueMap[firstSyntaxError]!!
    }
  }

  return checksum
}
