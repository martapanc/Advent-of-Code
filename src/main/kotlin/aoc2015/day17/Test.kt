package aoc2015.day17


fun main(args: Array<String>) {
    val cannes = intArrayOf(43, 3, 4, 10, 21, 44, 4, 6, 47, 41, 34, 17, 17, 44, 36, 31, 46, 9, 27, 38)
    var totalWay = 0
    var minContainer = cannes.size
    for (i in 1..(1 shl cannes.size)) {
        var container = 0
        var total = 0
        for (j in cannes.indices) {
            if (i shr j and 1 > 0) {
                container++
                total += cannes[j]
            }
        }
        if (total == 150) {
            if (minContainer > container) {
                minContainer = container
                totalWay = 1
            } else if (minContainer == container) {
                totalWay++
            }
        }
    }
    println("$totalWay $minContainer")
}
