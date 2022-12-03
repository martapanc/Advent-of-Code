package util

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Calendar

fun main(args: Array<String>) {
    AoCDailySetup().run(args)
}

class AoCDailySetup {

    fun run(args: Array<String>) {
        var year = Calendar.getInstance().get(Calendar.YEAR)
        var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        if (args.isNotEmpty()) {
            year = Integer.parseInt(args[0])
            day = Integer.parseInt(args[1])
        }

        val paddedDay = day.toString().padStart(2, '0')
        val dailyDir = "aoc${year}/day${paddedDay}"
        val dailyPackage = "aoc${year}.day${paddedDay}"
        val mainDir = "src/main/kotlin/${dailyDir}"
        val testDir = "src/test/kotlin/${dailyDir}"

        Files.createDirectories(Paths.get(mainDir))
        Files.createDirectories(Paths.get("${mainDir}/assets"))
        Files.createDirectories(Paths.get(testDir))

        val mainContent = "" +
            "package ${dailyPackage}\n" +
            "\n" +
            "fun part1(list: List<String>): Int {\n" +
            "    return 0\n" +
            "}\n" +
            "\n" +
            "fun part2(list: List<String>): Int {\n" +
            "    return 0\n" +
            "}\n"
        val mainClass = File("${mainDir}/Day${day}.kt")
        mainClass.printWriter().use { it.println(mainContent) }

        val testInput = File("${mainDir}/assets/input0")
        testInput.printWriter()

        val input = File("${mainDir}/assets/input")
        input.printWriter()

        val testContent = "" +
            "package ${dailyPackage}\n" +
            "\n" +
            "import org.junit.jupiter.api.Assertions.assertEquals\n" +
            "import org.junit.jupiter.api.Test\n" +
            "import util.readInputLineByLine\n" +
            "\n" +
            "internal class Day${day}KtTest {\n" +
            "\n" +
            "    private val testInput0 = readInputLineByLine(\"src/main/kotlin/${dailyDir}/assets/input0\")\n" +
            "    private val testInput = readInputLineByLine(\"src/main/kotlin/${dailyDir}/assets/input\")\n" +
            "\n" +
            "    @Test\n" +
            "    fun testPart1() {\n" +
            "        assertEquals(157, part1(testInput0))\n" +
            "        assertEquals(8085, part1(testInput))\n" +
            "    }\n" +
            "\n" +
            "    @Test\n" +
            "    fun testPart2() {\n" +
            "        assertEquals(70, part2(testInput0))\n" +
            "        assertEquals(2515, part2(testInput))\n" +
            "    }\n" +
            "}\n"

        val testClass = File("${testDir}/Day${day}KtTest.kt")
        testClass.printWriter().use { it.println(testContent) }
    }
}
