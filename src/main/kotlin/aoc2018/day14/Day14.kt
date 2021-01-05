package aoc2018.day14

fun generateRecipes(max: Int): String {
    val recipeList = mutableListOf(3, 7)
    var index1 = 0
    var index2 = 1
    repeat(max + 10) {
        val recipesToAdd = (recipeList[index1] + recipeList[index2]).toString().map { Character.getNumericValue(it) }
        recipeList.addAll(recipesToAdd)
        index1 = (index1 + 1 + recipeList[index1]) % recipeList.size
        index2 = (index2 + 1 + recipeList[index2]) % recipeList.size
    }
    return recipeList.subList(max, max + 10).joinToString("")
}

fun generateRecipesPart2(pattern: String): Int {
    val recipeList = mutableListOf(3, 7)
    var index1 = 0
    var index2 = 1
    for(i in 0L..10000000) {
        val recipesToAdd = (recipeList[index1] + recipeList[index2]).toString().map { Character.getNumericValue(it) }
        recipeList.addAll(recipesToAdd)
        index1 = (index1 + 1 + recipeList[index1]) % recipeList.size
        index2 = (index2 + 1 + recipeList[index2]) % recipeList.size
    }
    val recipeString = recipeList.joinToString("")

    val indicesList = recipeString.indicesOf(pattern)
    return indicesList[0]
}

fun String?.indicesOf(substr: String, ignoreCase: Boolean = true): List<Int> {
    return this?.let {
        val regex = if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr)
        regex.findAll(this).map { it.range.first }.toList()
    } ?: emptyList()
}