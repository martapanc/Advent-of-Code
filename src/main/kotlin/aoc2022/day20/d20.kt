package aoc2022.day20


//fun readInputToNumbers(path: String): List<Int> = readInputLineByLine(path).map { it.toInt() }

//fun part1(numbers: List<Int>): Int {
//    val array = numbers.toTypedArray()
//    val size = numbers.size
//    var sortedNumbers = numbers.toMutableList()
//    numbers.forEachIndexed { index, num ->
//        sortedNumbers = move(num, sortedNumbers)
//    }
//    val indexOfZero = sortedNumbers.indexOf(0)
//    val pos1000 = (indexOfZero + 1000) % size
//    val pos2000 = (indexOfZero + 2000) % size
//    val pos3000 = (indexOfZero + 3000) % size
//    return sortedNumbers[pos1000] + sortedNumbers[pos2000] + sortedNumbers[pos3000]
//}
//
//fun part2(numbers: List<Int>): Long {
//    return 0
//}
//
//fun move(number: Int, list: List<Int>): MutableList<Int> {
//    var newList = list.toMutableList()
//    val times = abs(number)
//    repeat(times) {
//        newList = if (number > 0) {
//            moveRight(number, newList)
//        } else {
//            moveLeft(number, newList)
//        }
//    }
//    return newList
//}
//
//fun moveRight(number: Int, list: List<Int>): MutableList<Int> {
//    val rightNumIndex = (list.indexOf(number) + 1) % list.size
//    val newList = list.toMutableList()
//    Collections.swap(newList, list.indexOf(number), rightNumIndex)
//    return newList
//}
//
//fun moveLeft(number: Int, list: List<Int>): MutableList<Int> {
//    val indexOfNum = list.indexOf(number)
//    val leftNumIndex = if (indexOfNum == 0) list.size - 1 else indexOfNum - 1
//    val newList = list.toMutableList()
//    Collections.swap(newList, indexOfNum, leftNumIndex)
//    return newList
//}
