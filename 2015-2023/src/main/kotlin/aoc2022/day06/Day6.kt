package aoc2022.day06

fun part1(list: String): Int {
    return solve(list, 4)
}

fun part2(list: String): Int {
    return solve(list, 14)
}

private fun solve(list: String, packetSize: Int): Int {
    for (i: Int in 0..list.length - packetSize) {
        val packet = list.subSequence(i, i + packetSize).toString()
        if (packet.allUnique()) {
            return i + packetSize
        }
    }
    return 0
}

private fun String.allUnique(): Boolean = all(hashSetOf<Char>()::add)
