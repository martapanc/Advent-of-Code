package aoc2023.day02

fun parse(lines: List<String>): List<Game> {
    val games = mutableListOf<Game>()
    lines.forEach { line ->
        val gameData = line.split(": ")
        val roundData = gameData[1].split("; ")
        val gameRounds = mutableListOf<Round>()
        roundData.forEach { round ->
            val colors = round.split(", ")
            val gameRound = Round()
            colors.forEach { color ->
                if (color.contains("blue")) {
                    gameRound.numBlue = extractNum(color)
                } else if (color.contains("green")) {
                    gameRound.numGreen = extractNum(color)
                } else {
                    gameRound.numRed = extractNum(color)
                }
            }
            gameRounds.add(gameRound)
        }
        games.add(Game(extractNum(gameData[0]), gameRounds))
    }
    return games
}

fun part1(games: List<Game>): Int {
    val bagContent = Round(14, 13, 12)
    var sum = 0
    games.forEach { game ->
        val highestVals = game.findHighestValues()
        if (highestVals["maxBlue"]!! <= bagContent.numBlue!!
            && highestVals["maxGreen"]!! <= bagContent.numGreen!!
            && highestVals["maxRed"]!! <= bagContent.numRed!!) {
            sum += game.id!!
        }
    }
    return sum
}

fun part2(games: List<Game>): Int {
    var sum = 0
    games.forEach { game ->
        val highestVals = game.findHighestValues()
        sum += highestVals["power"]!!
    }
    return sum
}

data class Game(val id: Int?, val rounds: List<Round>) {
    fun findHighestValues(): Map<String, Int> {
        val maxBlue = rounds.maxOfOrNull { it.numBlue ?: 0 } ?: 0
        val maxGreen = rounds.maxOfOrNull { it.numGreen ?: 0 } ?: 0
        val maxRed = rounds.maxOfOrNull { it.numRed ?: 0 } ?: 0

        return mapOf(
            "maxBlue" to maxBlue,
            "maxGreen" to maxGreen,
            "maxRed" to maxRed,
            "power" to maxBlue * maxGreen * maxRed
        )
    }
}

data class Round(var numBlue: Int? = 0, var numGreen: Int? = 0, var numRed: Int? = 0)

fun extractNum(input: String): Int? {
    val regex = Regex("\\d+")
    val matchResult = regex.find(input)
    return matchResult?.value?.toInt()
}
