package util

import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

fun main() {
    AoCStarsReader().updateReadme()
}

class AoCStarsReader {

    fun updateReadme() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        var template = "" +
                "# \uD83C\uDF84 Advent of Code ⛄️\n" +
                "![Java & Kotlin CI with Gradle](https://github.com/martapanc/Advent-of-Code/workflows/Java%20&%20Kotlin%20CI%20with%20Gradle/badge.svg)\n\n" +
                "Collection of my solutions to the [AoC](https://adventofcode.com/) challenges (2015-$currentYear)\n\n\n" +
                "## Quick links\n\n"
        val results = getResults()
        for ((year, stars) in results.entries) {
            val emojiYear = yearToEmojis(year)
            val perc = percentage(stars, 50)
            template += "- Advent of Code $emojiYear : [overview](src/main/kotlin/aoc$year/README.md) & [solutions](src/main/kotlin/aoc$year) - $stars / 50 ⭐️ &emsp; ![Progress](https://progress-bar.dev/$perc/) \n"
        }
        val sum = results.values.sum()
        val total = results.keys.count() * 50
        template += "\n Total:  $sum / $total ⭐"
        val totalPerc = percentage(sum, total)
        template += "\n\n ![Progress](https://progress-bar.dev/$totalPerc/)"
        template += "\n\n <img src=\"src/main/resources/static/300_stars.png\" width=\"920\"/>\n"

        File("README.md").printWriter().use { out -> out.println(template) }
    }

    private fun percentage(stars: Int, total: Int) = (stars.toDouble() / total * 100).toInt()

    private fun getResults(): Map<Int, Int> {
        val url = URL("${getConfigMapper().aocApiDataUrl}/stars")
        val map = mutableMapOf<Int, Int>()
        print("\nSending 'GET' request to URL : $url; ")
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"
            println("Response Code : $responseCode\")")
            val response = JSONArray(Scanner(inputStream).useDelimiter("'\\Z").next())
            for (item in response) {
                val year = (item as JSONObject).get("year") as Int
                val star = item.get("stars") as Int
                map[year] = star
            }
        }
        return map
    }

    private fun yearToEmojis(year: Int): String {
        val numberMap = mapOf(
            0 to "0️⃣",
            1 to "1️⃣",
            2 to "2️⃣",
            3 to "3️⃣",
            4 to "4️⃣",
            5 to "5️⃣",
            6 to "6️⃣",
            7 to "7️⃣",
            8 to "8️⃣",
            9 to "9️⃣"
        )
        var string = ""
        var num = year
        while (num > 0) {
            val emoji = numberMap[num % 10]
            string = "$emoji$string"
            num /= 10
        }
        return string
    }
}
