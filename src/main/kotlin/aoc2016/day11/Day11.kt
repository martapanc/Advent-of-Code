package aoc2016.day11

// Takes as input a list with the number of items on each floor, from 1 to 4.
// E.g. "The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
//The second floor contains a hydrogen generator. The third floor contains a lithium generator.
//The fourth floor contains nothing relevant." => list(2, 1, 1, 0)
fun solveGameOfGeneratorsAndChips(objectsPerFloor: List<Int>): Int {
    return (1 until objectsPerFloor.size)
        .map { 2 * objectsPerFloor.subList(0, it).sum() - 3 }
        .sum()
}