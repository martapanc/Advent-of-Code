package aoc2020.day22

import java.io.File

fun readInputToLists(path: String): Pair<List<Int>, List<Int>> {
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    val playerOne = mutableListOf<Int>()
    val playerTwo = mutableListOf<Int>()
    val iterator = lineList.iterator()
    while (iterator.hasNext()) {
        var line = iterator.next()
        if (line.startsWith("Player 1:")) {
            line = iterator.next()
            while (line != "") {
                playerOne.add(line.toInt())
                line = iterator.next()
            }
        }
        if (line.startsWith("Player 2:")) {
            line = iterator.next()
            while (line != "") {
                playerTwo.add(line.toInt())
                line = iterator.next()
            }
        }
    }
    return Pair(playerOne, playerTwo)
}

fun playGame(decks: Pair<List<Int>, List<Int>>): Long {
    val playerOne = decks.first.toMutableList()
    val playerTwo = decks.second.toMutableList()
    while (playerOne.size != 0 && playerTwo.size != 0) {
        val onePlays = playerOne.removeAt(0)
        val twoPlays = playerTwo.removeAt(0)
        if (onePlays > twoPlays) {
            playerOne.add(onePlays)
            playerOne.add(twoPlays)
        } else {
            playerTwo.add(twoPlays)
            playerTwo.add(onePlays)
        }
    }
    return findWinnerScore(playerOne, playerTwo)
}

fun playGameV2(decks: Pair<List<Int>, List<Int>>): Long {
    return findWinnerScore(playRecursiveGame(decks.first, decks.second).winningDeck, listOf())
}

fun playRecursiveGame(first: List<Int>, second: List<Int>): RecursiveGameWin {
    val playerOne = first.toMutableList()
    val playerTwo = second.toMutableList()
    val playerOneDeckHistory = mutableListOf<String>()
    val playerTwoDeckHistory = mutableListOf<String>()
    while (playerOne.size != 0 && playerTwo.size != 0) {
        if (playerOneDeckHistory.contains(deckToString(playerOne)) && playerTwoDeckHistory.contains(deckToString(playerTwo))) {
            return RecursiveGameWin(1, playerOne)
        }
        playerOneDeckHistory.add(deckToString(playerOne))
        playerTwoDeckHistory.add(deckToString(playerTwo))

        val onePlays = playerOne.removeAt(0)
        val twoPlays = playerTwo.removeAt(0)
        if (playerOne.size >= onePlays && playerTwo.size >= twoPlays) {
            val recursiveGame = playRecursiveGame(playerOne.subList(0, onePlays), playerTwo.subList(0, twoPlays))
            if (recursiveGame.winningPlayerNum == 1) {
                playerOne.add(onePlays)
                playerOne.add(twoPlays)
            } else {
                playerTwo.add(twoPlays)
                playerTwo.add(onePlays)
            }
        } else {
            if (onePlays > twoPlays) {
                playerOne.add(onePlays)
                playerOne.add(twoPlays)
            } else {
                playerTwo.add(twoPlays)
                playerTwo.add(onePlays)
            }
        }
    }
    val winningPlayerNum = if (playerOne.isNotEmpty()) 1 else 2
    val winningDeck = if (playerOne.isNotEmpty()) playerOne else playerTwo
    return RecursiveGameWin(winningPlayerNum, winningDeck)
}

private fun findWinnerScore(playerOne: List<Int>, playerTwo: List<Int>): Long {
    val winner = if (playerOne.isNotEmpty()) playerOne else playerTwo
    return (winner.size downTo 1)
        .mapIndexed { index, num -> (num * winner[index]).toLong() }
        .sum()
}

private fun deckToString(deck: List<Int>): String {
    var string = ""
    for (card in deck) string += "$card "
    return string.trim()
}

data class RecursiveGameWin(var winningPlayerNum: Int, var winningDeck: List<Int>)