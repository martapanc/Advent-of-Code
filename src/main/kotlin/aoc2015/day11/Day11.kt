package aoc2015.day11

fun findNextValidPassword(inputPassword: String): String {
    val passwordArray = inputPassword.toCharArray()
    while (true) {
        increment(passwordArray)
        if (isValidPassword(String(passwordArray))) {
            return String(passwordArray)
        }
    }
}

private fun increment(passwordArray: CharArray, position: Int = passwordArray.size -1 ) {
    passwordArray[position] = incrementCharacter(passwordArray[position])
    var i = position
    while (passwordArray[i] == 'a' && i > 0) {
        i--
        passwordArray[i] = incrementCharacter(passwordArray[i])
    }
}

fun incrementCharacter(char: Char): Char {
    return when (char) {
        'h' -> 'j'
        'k' -> 'm'
        'n' -> 'p'
        'z' -> 'a'
        else -> (char.toInt() + 1).toChar()
    }
}

fun isValidPassword(password: String): Boolean {
    val threeLetterSequence = Regex("""[a-z]*(abc|bcd|cde|def|efg|fgh|pqr|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz)[a-z]*""")
    val twoLetterPairs = mutableListOf("aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "jj", "kk", "mm", "nn", "pp", "qq", "rr", "ss", "tt", "uu", "vv", "ww", "xx", "yy", "zz")
    val forbiddenLetters = listOf('i', 'l', 'o')
    var containsForbiddenLetters = false
    for (char in password) {
        if (forbiddenLetters.contains(char)) {
            containsForbiddenLetters = true
            break
        }
    }

    var matchesTwoLettersFirst = false
    var match = ""
    for (pair in twoLetterPairs)
        if (password.contains(pair)) {
            matchesTwoLettersFirst = true
            match = pair
            break
        }
    val tempPassword = password.replace(match, "-")
    twoLetterPairs.remove(match)
    val matchesTwoLettersSecond = twoLetterPairs.any { tempPassword.contains(it) }
    return password.matches(threeLetterSequence) && matchesTwoLettersFirst && matchesTwoLettersSecond && !containsForbiddenLetters
}