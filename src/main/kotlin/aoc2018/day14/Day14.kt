package aoc2018.day14

fun generateRecipes(max: Int): String {
    val scoreList = mutableListOf(3, 7)
    var index1 = 0
    var index2 = 1
    repeat(max + 10) {
        val recipesToAdd = (scoreList[index1] + scoreList[index2]).toString().map { Character.getNumericValue(it) }
        scoreList.addAll(recipesToAdd)
        index1 = (index1 + 1 + scoreList[index1]) % scoreList.size
        index2 = (index2 + 1 + scoreList[index2]) % scoreList.size
    }
    return scoreList.subList(max, max + 10).joinToString("")
}

fun generateRecipesPart2(pattern: Int): Int {
    val recipeList = mutableListOf(3, 7)
    var index1 = 0
    var index2 = 1
    repeat(pattern * 17) {
        val recipesToAdd = (recipeList[index1] + recipeList[index2]).toString().map { Character.getNumericValue(it) }
        recipeList.addAll(recipesToAdd)
        index1 = (index1 + 1 + recipeList[index1]) % recipeList.size
        index2 = (index2 + 1 + recipeList[index2]) % recipeList.size
    }
    val recipeString = recipeList.joinToString("")

    return recipeString.indicesOf(pattern.toString())
}

fun String?.indicesOf(substr: String, ignoreCase: Boolean = true): Int {
    return this?.let {
        val regex = if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr)
        regex.findAll(this).map { it.range.first }.toList()[0]
    } ?: -1
}
