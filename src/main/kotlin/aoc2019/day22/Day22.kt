package aoc2019.day22

import util.readInputLineByLine

fun runNaiveShuffler(path: String, deckSize: Int = 10007, outputPos: Int = 2019): Int {
    val deck = IntArray(deckSize) { it }
    for (line in readInputLineByLine(path)) {
        when {
            line.startsWith("cut") -> {
                val cutValue = line.split(" ")[1].toInt()
                val newDeck = IntArray(deckSize) { c ->
                    deck[c + cutValue modulo deckSize]
                }
                newDeck.copyInto(deck)
            }
            line.startsWith("deal with increment") -> {
                val incrementValue = line.split(" ")[3].toInt()
                val newDeck = IntArray(deckSize)
                var index = 0
                repeat(deckSize) { i ->
                    newDeck[index] = deck[i]
                    index += incrementValue
                    index %= deckSize
                }
                newDeck.copyInto(deck)
            }
            line == "deal into new stack" -> {
                deck.reverse()
            }
        }
    }
    return deck.indexOf(outputPos)
}

infix fun Int.modulo(mod: Int): Int = (this % mod).let { (it shr Int.SIZE_BITS - 1 and mod) + it }
