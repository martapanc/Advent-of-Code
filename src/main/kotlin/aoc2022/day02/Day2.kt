package aoc2022.day02

import util.readInputLineByLine

fun readInputToPairOfPlays(path: String): List<Pair<Play, Play>> {
    val pairsOfPlays: MutableList<Pair<Play, Play>> = mutableListOf()

    readInputLineByLine(path).forEach { line ->
        val split = line.split(" ")
        val opponent = EncodedPlay.valueOf(split[0])
        val player = EncodedPlay.valueOf(split[1])
        pairsOfPlays.add(Pair(decodePlay(opponent), decodePlay(player)))
    }
    return pairsOfPlays
}

fun scissorPaperRockPart1(plays: List<Pair<Play, Play>>): Int {
    var score = 0
    val opponentVsPlayerMap: Map<Pair<Play, Play>, Int> = mapOf(
        Pair(Play.SCISSOR, Play.SCISSOR) to 3,
        Pair(Play.PAPER, Play.SCISSOR) to 6,
        Pair(Play.ROCK, Play.SCISSOR) to 0,
        Pair(Play.SCISSOR, Play.PAPER) to 0,
        Pair(Play.PAPER, Play.PAPER) to 3,
        Pair(Play.ROCK, Play.PAPER) to 6,
        Pair(Play.SCISSOR, Play.ROCK) to 6,
        Pair(Play.PAPER, Play.ROCK) to 0,
        Pair(Play.ROCK, Play.ROCK) to 3,
    )
    val objectScore: Map<Play, Int> = mapOf(
        Play.ROCK to 1,
        Play.PAPER to 2,
        Play.SCISSOR to 3,
    )
    plays.forEach { play -> score += opponentVsPlayerMap[play]!! + objectScore[play.second]!! }
    return score
}


enum class Play { SCISSOR, PAPER, ROCK }

enum class Outcome { WIN, DRAW, LOSE }

enum class EncodedPlay { A, B, C, X, Y, Z }

fun decodePlay(encodedPlay: EncodedPlay): Play {
    return when (encodedPlay) {
        EncodedPlay.A, EncodedPlay.X -> Play.ROCK
        EncodedPlay.B, EncodedPlay.Y -> Play.PAPER
        EncodedPlay.C, EncodedPlay.Z -> Play.SCISSOR
    }
}

fun decodeOpponentPlay(encodedPlay: EncodedPlay): Play {
    return when (encodedPlay) {
        EncodedPlay.A -> Play.ROCK
        EncodedPlay.B -> Play.PAPER
        EncodedPlay.C -> Play.SCISSOR
        else -> Play.ROCK
    }
}

fun decodePlayerPlay(encodedPlay: EncodedPlay): Outcome {
    return when (encodedPlay) {
        EncodedPlay.X -> Outcome.LOSE
        EncodedPlay.Y -> Outcome.DRAW
        EncodedPlay.Z -> Outcome.WIN
        else -> Outcome.LOSE
    }
}
