package aoc2016.day23


// Observing what the input is doing, the output corresponds to the values highest values stored in a cpy and jnz
// multiplied, then adding eggs! to the result
// E.g.
// cpy 79 c
// jnz 74 d
fun computeAssembunnyRegisterValue(eggs: Int, first: Int, second: Int): Long {
    return factorial(eggs) + first * second
}

fun factorial(n: Int): Long {
    var factorial = 1L
    for (i in 1..n) {
        factorial *= i.toLong()
    }
    return factorial
}