package aoc2021.day10

fun findSyntaxErrorChecksum(inputList: List<String>, isPart2: Boolean = false): Long {
  var checksum = 0
  val valueMap = mutableMapOf(')' to 3, ']' to 57, '}' to  1197, '>' to 25137)
  val valueMap2 = mutableMapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)

  val totalList = mutableListOf<Long>()

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
    } else {
      var total = 0L
      tempLine.reversed().forEach { parenthesis ->
        total = 5 * total + valueMap2[parenthesis]!!
      }
      totalList.add(total)
    }
  }

  totalList.sort()
  return if (!isPart2) checksum.toLong() else totalList[totalList.size / 2]
}
