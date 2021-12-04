package aoc2021.day04

fun readInputToSquidBingo(input: List<String>): Pair<List<Int>, List<SquidBingoBoard>> {
    val numbersDrawn: List<Int> = input[0].split(",").map { it.toInt() }
    val boardList = mutableListOf<SquidBingoBoard>()

    (2 until input.size step 6).forEach { index: Int ->
        val boardNumbers = mutableListOf<BingoNumber>()

        (index until index + 5)
            .map { localIndex ->
                input[localIndex].trim().split("\\s+".toRegex()).map {
                    BingoNumber(it.toInt())
                }
            }
            .forEach { boardNumbers.addAll(it) }
        boardList.add(SquidBingoBoard(boardNumbers))
    }

    return Pair(numbersDrawn, boardList)
}

fun playSquidBingo(numbersDrawn: List<Int>, boards: List<SquidBingoBoard>, isPart2: Boolean = false): Int {
    val boardsNumber = boards.size
    var winningBoardCount = 0

    numbersDrawn.forEach { numDrawn ->

        for (board: SquidBingoBoard in boards) {
            if (!board.isWon) {
                board.bingoNumbers
                    .find { bingoNumber -> bingoNumber.number == numDrawn }
                    ?.drawn = true

                val isBingoWin = isBingoWin(board, numDrawn)
                if (isBingoWin.isWin) {
                    if (!isPart2) {
                        return numDrawn * isBingoWin.sumOfUnmarkedNumbers
                    } else {
                        winningBoardCount++
                        board.isWon = true
                        if (winningBoardCount == boardsNumber) {
                            return numDrawn * isBingoWin.sumOfUnmarkedNumbers
                        }
                    }
                }
            }
        }
    }

    return -1
}

fun isBingoWin(board: SquidBingoBoard, lastNumDrawn: Int): BingoWin {
    val rows = board.bingoNumbers.chunked(5)
    val columns = (0 until 5).map { index -> rows.map { it[index] } }

    for (combo: List<BingoNumber> in rows + columns) {
        if (combo.all { it.drawn }) {
            return BingoWin(
                isWin = true,
                sumOfUnmarkedNumbers = board.bingoNumbers.filter { !it.drawn }.sumBy { it.number },
                lastNumDrawn = lastNumDrawn
            )
        }
    }
    return BingoWin(isWin = false)
}

class SquidBingoBoard(val bingoNumbers: List<BingoNumber>, var isWon: Boolean = false)

class BingoNumber(val number: Int, var drawn: Boolean = false) {
    override fun toString(): String {
        return "{number: $number, drawn: $drawn}"
    }
}

class BingoWin(val isWin: Boolean, val sumOfUnmarkedNumbers: Int = -1, val lastNumDrawn: Int = -1)