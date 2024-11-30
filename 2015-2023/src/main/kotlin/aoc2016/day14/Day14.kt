package aoc2016.day14

import aoc2016.day05.md5
import kotlin.reflect.KFunction1

fun getIndexProducing64thKey(inputSalt: String, isPart2: Boolean = false): Int {
    val hashCache = mutableMapOf<String, String>()
    val threeRegex = Regex("([0-9a-z\\d])\\1\\1")
    var keyCount = 0
    var index = 0
    var currentIndex = index
    val hashMethod = if (isPart2) ::md5Extended else ::md5

    while (keyCount < 64) {
        currentIndex = index
        val input = inputSalt + index++
        val hash: String = getHash(input, hashMethod, hashCache)
        if (!hash.contains(threeRegex)) {
            continue
        }
        val fiveKey = threeRegex.find(hash)!!.groupValues[1].repeat(5)
        if ((index..index + 999).any { getHash( inputSalt + it, hashMethod, hashCache).contains(fiveKey) })
            keyCount++
    }
    return currentIndex
}

private fun getHash(
    input: String,
    hashMethod: KFunction1<String, String>,
    hashCache: MutableMap<String, String>
): String {
    val hash: String
    if (hashCache.containsKey(input)) {
        hash = hashCache[input]!!
    } else {
        hash = hashMethod(input)
        hashCache[input] = hash
    }
    return hash
}

fun md5Extended(inputHash: String): String {
    var output = inputHash
    for (i in 1..2017) {
        output = md5(output)
    }
    return output
}