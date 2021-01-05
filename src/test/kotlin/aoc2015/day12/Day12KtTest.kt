package aoc2015.day12

import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day12KtTest {

    private val path = "src/main/kotlin/aoc2015/day12/input.json"

    @Test
    fun testReadJson() {
        assertEquals(119433, readJsonAndComputeSum(path))
    }

    @Test
    fun testRecursiveJson() {
        assertEquals(3, recursiveJson(JSONObject("""{"b":"green","c":3}""")))
        assertEquals(8, recursiveJson(JSONObject("""{"b":[{"a":4,"f":1}],"c":3}""")))
        assertEquals(13, recursiveJson(JSONObject("""{"b":[{"a":4,"f":{"a":6,"d":"red"}}],"c":3}""")))
        assertEquals(7, recursiveJson(JSONArray("""[{"b":"green","c":2},{"b":"green","c":5}]""")))
        assertEquals(0, recursiveJson(JSONObject("""{"a":[-1,1]}""")))
        assertEquals(0, recursiveJson(JSONObject("""{}""")))
        assertEquals(0, recursiveJson(JSONArray("""[]""")))
        assertEquals(3, recursiveJson(JSONArray("""[[[3]]]""")))
    }
}