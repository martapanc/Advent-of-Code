package aoc2017.day09

fun countGroupsAndComputeScore(inputString: String): Int {
    var string = inputString
    string = string.replace(Regex("!."), "") // Remove characters that are ignored
    string = string.replace(Regex("<[^>.]*>"), "") // Remove garbage groups
    string = string.replace(",", "") // Replace commas for easier group count

    val stack = ArrayDeque<Char>()
    var groupScore = 0
    var depth = 0
    for (char in string) {
        if (char == '}') {
            stack.removeLast()
            groupScore += depth
            depth--
        } else {
            depth++
            stack.add(char)
        }
    }
    return groupScore
}

fun countItemsInGarbage(inputString: String): Int {
    var string = inputString
    string = string.replace(Regex("!."), "")
    val garbageContent = Regex("<([^>.]*)>")
    val matchList = garbageContent.findAll(string).toList().map { it.groupValues[1] }
    return matchList.sumBy { it.length }
}
