package aoc2016.day25

// Given the two highest number in cpy instructions (e.g. 9 and 282) this is what the Assembunny code does:
// - multiply the 2 numbers
// - integer divide by 2 where the result is be used for next iteration
// - if the result is even or odd it will produce respectively 0 or 1

fun runAssembunnyCode(first: Int, second: Int): Int {
    val magicNumber = first * second
    var count = 1
    while (count < magicNumber) {
        count = if (count % 2 == 0) count * 2 + 1 else count * 2
    }
    return count - magicNumber
}