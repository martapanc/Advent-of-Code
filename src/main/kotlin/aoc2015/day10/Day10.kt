package aoc2015.day10

fun lookAndSay(input: String): String {
    var expandedString: String = input
    repeat(40) {
        val split = expandedString.split(Regex("(?<=(.))(?!\\1)")).toTypedArray().filter { it.isNotEmpty() }
        var output = ""
        for (item in split) {
            output += "" + item.length + item.first()
        }
        expandedString = output
    }
    return expandedString
}

fun lookAndSayV2(input: String, times: Int): String {
    var expanded = input
    repeat(times) {
        val output = StringBuilder()
        var i = 0
        while (i < expanded.length) {
            var count = 1
            val char = expanded[i]
            while (i + 1 < expanded.length && expanded[i + 1] == char) {
                i++
                count++
            }
            output.append(count)
            output.append(char)
            i++
        }
        expanded = output.toString()
    }
    return expanded
}