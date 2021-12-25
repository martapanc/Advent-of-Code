package aoc2021.day21

fun playNormalDice(playerPos: Pair<Int, Int>): Int {
  var player1Pos = playerPos.first
  var player1Score = 0
  var player2Pos = playerPos.second
  var player2Score = 0
  var dieValue = 1
  var numOfRolls = 0
  var player1Turn = true
  game@ while (true) {
    var score = 0
    repeat(3) {
      score += dieValue
      dieValue++
      numOfRolls++
      if (dieValue == 101) {
        dieValue = 1
      }
    }

    if (player1Turn) {
      player1Pos = (player1Pos + score) % 10
      player1Score += if (player1Pos == 0) 10 else player1Pos
      if (player1Score >= 1000) break@game
    } else {
      player2Pos = (player2Pos + score) % 10
      player2Score += if (player2Pos == 0) 10 else player2Pos
      if (player2Score >= 1000) break@game
    }

    player1Turn = !player1Turn
  }

  return numOfRolls * if (player1Turn) player2Score else player1Score
}

fun playDiracDice(playerPos: Pair<Int, Int>): Long {
  val (p1Wins, p2Wins) = play(playerPos.first, 0, playerPos.second, 0, 21)
  return if (p1Wins > p2Wins) p1Wins else p2Wins
}

fun play(p1Pos: Int, p1Score: Int, p2Pos: Int, p2Score: Int, maxScore: Int): Pair<Long, Long> {
  if (p1Score >= maxScore) return Pair(1, 0)
  if (p2Score >= maxScore) return Pair(0, 1)

  var p1Wins = 0L
  var p2Wins = 0L

  for (rollScore in getQuantumRollScores()) {
    val newPos = (p1Pos + rollScore) % 10
    val newScore = p1Score + newPos + 1
    val (p1WinAcc, p2WinAcc) = play(p2Pos, p2Score, newPos, newScore, maxScore)
    p1Wins += p1WinAcc
    p2Wins += p2WinAcc
  }

  return Pair(p1Wins, p2Wins)
}

fun getQuantumRollScores(): List<Int> {
  val list = mutableListOf<Int>()
  (1..3).forEach { d1 ->
    (1..3).forEach { d2 ->
      (1..3).forEach { d3 ->
        list.add(d1 + d2 + d3)
      }
    }
  }
  return list
}
