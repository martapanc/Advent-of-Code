package aoc2023.day04

import kotlin.math.pow

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
        val wins = countWins(game)
        if (wins.isNotEmpty()) {
            sum += (2.0.pow((wins.size - 1).toDouble())).toInt()
        }
    }
    return sum
}

fun part2(games: List<Game>): Long {
    games.forEachIndexed { index, game ->
        val wins = countWins(game).size
        (0 until wins).forEach { i ->
            games[index + i + 1].quantity += game.quantity
        }
    }
    return games.sumOf { it.quantity }
}

private fun countWins(game: Game): Set<Int> {
    return game.winning.numbers.intersect(game.myNumbers.numbers.toSet())
}

data class Game(val id: Int, val winning: Winning, val myNumbers: MyNumbers, var quantity: Long = 1)

data class Winning(val numbers: List<Int>)
data class MyNumbers(val numbers: List<Int>)
