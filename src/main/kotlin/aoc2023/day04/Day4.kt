package aoc2023.day04

fun parse(list: List<String>): List<Game> {
    val games = mutableListOf<Game>()
    list.forEach {
        val split = it.split(" | ")
        val card: List<Int> = split[0]
            .replace("Card", "")
            .replace(":", "")
            .trim()
            .split("\\s+".toRegex())
            .map { str -> Integer.parseInt(str) }
        val numbers = split[1]
            .trim()
            .split("\\s+".toRegex())
            .map { str -> Integer.parseInt(str) }
        games.add(Game(card[0], Winning(card.subList(1, card.size)), MyNumbers(numbers)))
    }
    return games
}

fun part1(games: List<Game>): Int {
    var sum = 0
    games.forEach { game ->
        val wins = game.winning.numbers.intersect(game.myNumbers.numbers.toSet())
        if (wins.isNotEmpty()) {
            sum += (Math.pow(2.0, (wins.size - 1).toDouble())).toInt()
        }
    }
    return sum
}

fun part2(list: List<Game>): Int {
    return 0
}

data class Game (val id: Int, val winning: Winning, val myNumbers: MyNumbers)

data class Winning(val numbers: List<Int>)
data class MyNumbers(val numbers: List<Int>)
