package aoc2015.day25

//   | 1   2   3   4   5   6   7
//---+---+---+---+---+---+---+---+
// 1 |  1   3   6  10  15  21  28
// 2 |  2   5   9  14  20  27
// 3 |  4   8  13  19  26
// 4 |  7  12  18  25
// 5 | 11  17  24
// 6 | 16  23
// 7 | 22

// Sum of first N numbers
fun computeValueOfNAtX(x: Int): Int {
    return x * (x + 1) / 2
}

// Sum of first N-1 numbers, plus 1
fun computeValueOfNAtY(y: Int): Int {
    return (y - 1) * y / 2 + 1
}

fun computeValueOfNAtXY(x: Int, y: Int): Int {
    return computeValueOfNAtX(x + (y - 2)) + x
}

fun computeNthCode(initialCode: Long, n: Int): Long {
    var outputCode = initialCode
    repeat(n - 1) {
        outputCode = outputCode * 252533 % 33554393
    }
    return outputCode
}