package aoc2015.day19

import java.util.*

val lineRegex = Regex("\\s=>\\s")

fun countDistinctMolecules(replacements: List<String>, molecule: String): Int {
    val molecules = mutableSetOf<String>()

    for (reverseRepl in reverseReplacements(replacements)) {
        var index = -1
        do {
            index = molecule.indexOf(reverseRepl.value, index + 1)
            if (index != -1) {
                molecules.add(
                    molecule.replaceRange(index, index + reverseRepl.value.length, reverseRepl.key)
                )
            }
        } while (index != -1)
    }
    return molecules.size
}

fun findStepsToCreateMolecules(replacements: List<String>, molecule: String): Int {
    var count = -1
    val reversedReplacements = reverseReplacements(replacements)

    do {
        count = findMolecule(0, molecule, reversedReplacements)
    } while (count == -1)
    return count
}

private tailrec fun findMolecule(depth: Int, molecule: String, replacements: Map<String, String>): Int {
    if (molecule == "e")
        return depth
    else {
        var initialMolecule = molecule
        val keys = replacements.keys.toMutableList()
        var isReplaced = false
        while (!isReplaced) {
            val toReplace = keys.removeAt(Random().nextInt(keys.size))
            if (molecule.contains(toReplace)) {
                val before = initialMolecule.substringBefore(toReplace)
                val after = initialMolecule.substringAfter(toReplace)
                initialMolecule = before + replacements[toReplace] + after
                isReplaced = true
            }
            if (keys.isEmpty())
                return -1
        }
        return findMolecule(depth + 1, initialMolecule, replacements)
    }
}

private fun reverseReplacements(replacements: List<String>) =
    replacements.associateBy({ it.split(lineRegex)[1] }, { it.split(lineRegex)[0] })