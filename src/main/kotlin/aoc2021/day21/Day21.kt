package aoc2021.day21

fun playDiracDice(playerPos: Pair<Int, Int>): Int {
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
