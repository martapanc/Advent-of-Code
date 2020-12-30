package aoc2020.day25

const val constant = 20201227
const val subjectNum = 7

fun findEncryptionKey(doorPubKey: Int, cardPubKey: Int): Long {
    val cardLoopNum = findLoopNumber(cardPubKey)
    val doorLoopNum = findLoopNumber(doorPubKey)

    val encryptionKey = transform(doorPubKey, cardLoopNum)
    if (encryptionKey == transform(cardPubKey, doorLoopNum))
        return encryptionKey
    return -1
}

private fun findLoopNumber(cardPubKey: Int): Int {
    var computedPubKey = 1
    var loopNum = 0
    while (computedPubKey != cardPubKey) {
        computedPubKey *= subjectNum
        computedPubKey %= constant
        loopNum++
    }
    return loopNum
}

private fun transform(subjectNum: Int, loopNumber: Int): Long {
    var computedKey = 1L
    for (i in 0 until loopNumber) {
        computedKey *= subjectNum
        computedKey %= constant
    }
    return computedKey
}