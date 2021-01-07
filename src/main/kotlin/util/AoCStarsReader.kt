package util

import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class AoCStarsReader {

    fun updateReadme() {
        var template = "" +
                "# \uD83C\uDF84 Advent of Code ⛄️\n" +
                "![Java & Kotlin CI with Gradle](https://github.com/martapanc/Advent-of-Code/workflows/Java%20&%20Kotlin%20CI%20with%20Gradle/badge.svg)\n\n" +
                "Collection of my solutions to the AoC challenges (2015-2020)\n\n\n" +
                "## Quick links\n\n"
        for ((year, stars) in getResults().entries) {
            val emojiYear = yearToEmojis(year)
            template += "- Advent of Code $emojiYear : [overview](src/main/kotlin/aoc$year/README.md) & [solutions](src/main/kotlin/aoc$year) - $stars / 50 ⭐️ \n"
        }

        File("README.md").printWriter().use { out -> out.println(template) }
    }

    private fun getResults(): Map<Int, Int> {
        val url = URL("https://aoc-data-api.herokuapp.com/stars")
        val map = mutableMapOf<Int, Int>()
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"  // optional default is GET
            println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")
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


fun main() {
    AoCStarsReader().updateReadme()
}