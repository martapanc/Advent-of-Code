package aoc2022.day06

fun part1(list: String): Int {
    for (i : Int in 0 .. list.length - 4) {
        val packet = list.subSequence(i, i + 4).toString()
        if (packet.allUnique()) {
            return i + 4
        }
    }
    return 0
}

fun part2(list: String): Int {
    for (i : Int in 0 .. list.length - 14) {
        val packet = list.subSequence(i, i + 14).toString()
        if (packet.allUnique()) {
            return i + 14
        }
    }
    return 0
}

private fun String.allUnique(): Boolean = all(hashSetOf<Char>()::add)
