package aoc2023.day07

fun parse(lines: List<String>): List<String> {
    val output = mutableListOf<String>()
    lines.forEach { line ->

    }
    return output
}

fun part1(input: List<String>): Long {
    return 0
}

fun part2(input: List<String>): Long {
    return 0
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
