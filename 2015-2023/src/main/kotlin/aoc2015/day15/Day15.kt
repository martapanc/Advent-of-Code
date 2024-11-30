package aoc2015.day15

fun findOptimalCombination(count500Calories: Boolean = false): Int {
    var maxValue = 0
    for (a in 1..100)
        for (b in 1..(100 - a))
            for (c in 1..(100 - a - b)) {
                val currentValue = getTotal(listOf(a, b, c, 100 - a - b - c), count500Calories)
                if (currentValue > maxValue) {
                    maxValue = currentValue
                }
            }
    return maxValue
}

// Sugar:       capacity 3,   durability 0,  flavor 0,  texture -3,  calories 2
// Sprinkles:   capacity -3,  durability 3,  flavor 0,  texture 0,   calories 9
// Candy:       capacity -1,  durability 0,  flavor 4,  texture 0,   calories 1
// Chocolate:   capacity 0,   durability 0,  flavor -2, texture 2,   calories 8
fun getTotal(numbers: List<Int>, count500Calories: Boolean = false): Int {
    var capacity = 0
    var durability = 0
    var flavor = 0
    var texture = 0
    var calories = 0
    for ((index, num) in numbers.withIndex()) {
        capacity += num * listOf(3, -3, -1, 0)[index]
        durability += num * listOf(0, 3, 0, 0)[index]
        flavor += num * listOf(0, 0, 4, -2)[index]
        texture += num * listOf(-3, 0, 0, 2)[index]
        calories += num * listOf(2, 9, 1, 8)[index]
    }
    if (listOf(capacity, durability, flavor, texture).any { it < 0 } || (count500Calories && calories != 500)) {
        return 0
    }
    return capacity * durability * flavor * texture
}