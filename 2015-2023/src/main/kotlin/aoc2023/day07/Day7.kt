package aoc2023.day07

fun parse(lines: List<String>): List<Game> {
    val games = mutableListOf<Game>()
    lines.forEach { line ->
        val split = line.split(" ")
        games.add(Game(split[0], determineType(split[0]), split[1].toInt()))
    }
    return games
}

fun part1(games: List<Game>): Long {
    var total = 0L
    games.sortedWith(::compareHands).reversed()
        .forEachIndexed { index, game ->
            total += (index + 1) * game.bid
        }
    return total
}

fun part2(games: List<Game>): Long {
    var total = 0L
    games.sortedWith{ g1, g2 -> compareHands(g1, g2, true) }.reversed()
        .forEachIndexed { index, game ->
            total += (index + 1) * game.bid
        }
    return total
}

fun determineType(hand: String): HandType {
    val charCount = mutableMapOf<Char, Int>()
    for (char in hand) {
        charCount[char] = charCount.getOrDefault(char, 0) + 1
    }

    val pairCount = charCount.values.count { it == 2 }
    val threeOfAKind = charCount.values.contains(3)
    val fourOfAKind = charCount.values.contains(4)
    val fullHouse = threeOfAKind && pairCount > 0
    val fiveOfAKind = charCount.values.contains(5)

    return when {
        fiveOfAKind -> HandType.FIVE_OF_A_KIND
        fourOfAKind -> HandType.FOUR_OF_A_KIND
        fullHouse -> HandType.FULL_HOUSE
        threeOfAKind -> HandType.THREE_OF_A_KIND
        pairCount == 2 -> HandType.TWO_PAIR
        pairCount == 1 -> HandType.ONE_PAIR
        else -> HandType.HIGH_CARD
    }
}

private fun compareHands(game1: Game, game2: Game, withJolly: Boolean = false): Int {
    val typeComparison = if (withJolly)
        game2.jollyHandType.rank.compareTo(game1.jollyHandType.rank)
    else
        game2.handType.rank.compareTo(game1.handType.rank)

    if (typeComparison != 0) {
        return typeComparison
    }

    val cardValues = if (withJolly) "AKQT98765432J" else "AKQJT98765432"
    for (i in game1.hand.indices) {
        val card1 = game1.hand[i]
        val card2 = game2.hand[i]
        val valueComparison = cardValues.indexOf(card1).compareTo(cardValues.indexOf(card2))
        if (valueComparison != 0) {
            return valueComparison
        }
    }
    // Cards are equal
    return 0
}

enum class HandType(val rank: Int = 0) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1)
}

data class Game(val hand: String, val handType: HandType, val bid: Int) {
    val jollyHandType: HandType by lazy { calculateJollyHandType() }
    private fun calculateJollyHandType(): HandType {
        val jollyCount = hand.count { it == 'J' }
        if (jollyCount == 1) {
            when {
                handType == HandType.HIGH_CARD -> return HandType.ONE_PAIR
                handType == HandType.ONE_PAIR -> return HandType.THREE_OF_A_KIND
                handType == HandType.TWO_PAIR -> return HandType.FULL_HOUSE
                handType == HandType.THREE_OF_A_KIND -> return HandType.FOUR_OF_A_KIND
                handType == HandType.FOUR_OF_A_KIND -> return HandType.FIVE_OF_A_KIND
            }
        }
        if (jollyCount >= 2) {
            when {
                handType == HandType.ONE_PAIR -> return HandType.THREE_OF_A_KIND
                handType == HandType.TWO_PAIR -> return HandType.FOUR_OF_A_KIND
                handType == HandType.THREE_OF_A_KIND -> return HandType.FOUR_OF_A_KIND
                handType == HandType.FULL_HOUSE -> return HandType.FIVE_OF_A_KIND
                handType == HandType.FOUR_OF_A_KIND -> return HandType.FIVE_OF_A_KIND
            }
        }
        return handType
    }
}

fun compareHands(hand1: String, hand2: String): Int {
    val hand1Type = determineType(hand1)
    val hand2Type = determineType(hand2)
    val typeComparison = hand2Type.rank.compareTo(hand1Type.rank)

    if (typeComparison != 0) {
        return typeComparison
    }

    val cardValues = "AKQJT98765432"
    for (i in hand1.indices) {
        val card1 = hand1[i]
        val card2 = hand2[i]
        val valueComparison = cardValues.indexOf(card1).compareTo(cardValues.indexOf(card2))
        if (valueComparison != 0) {
            return valueComparison
        }
    }
    return 0
}
