package aoc2020.day4

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day4KtTest {

    private val inputFile = readInputFile("src/main/kotlin/aoc2020/day4/input")
    private val input0File = readInputFile("src/main/kotlin/aoc2020/day4/input0")
    private val input2File = readInputFile("src/main/kotlin/aoc2020/day4/input2")

    @Test
    fun testReadInputFile() {
        println(inputFile)
        println(input0File)
        assertEquals(4, input0File.size)
    }

    @Test
    fun testCountValidPassports() {
        assertEquals(2, countValidPassports(input0File, ::arePassportKeysValid))
        assertEquals(206, countValidPassports(inputFile, ::arePassportKeysValid))
    }

    @Test
    fun testIsPassportDataValid() {
        assertTrue(isPassportDataValid("pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2f"))
        assertTrue(isPassportDataValid("pid:087499704 hgt:155cm ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2f"))
        assertTrue(isPassportDataValid("pid:087499704 hgt:155cm ecl:oth iyr:2012 eyr:2030 byr:1980 hcl:#623a2f"))
        assertTrue(isPassportDataValid("pid:087499704 hgt:155cm ecl:oth iyr:2012 eyr:2025 byr:1995 hcl:#623a2f"))
        assertTrue(isPassportDataValid("pid:087499704 hgt:155cm ecl:oth iyr:2012 eyr:2025 byr:1995 hcl:#1235ad"))

        assertFalse(isPassportDataValid("pid:08749970 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2f"))
        assertFalse(isPassportDataValid("pid:0874997011 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2f"))

        assertFalse(isPassportDataValid("pid:087499704 hgt:74 ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2f"))
        assertFalse(isPassportDataValid("pid:087499704 hgt:0cm ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2f"))
        assertFalse(isPassportDataValid("pid:087499704 hgt:74cm ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2f"))
        assertFalse(isPassportDataValid("pid:087499704 hgt:100in ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2f"))

        assertFalse(isPassportDataValid("pid:087499704 hgt:74in ecl:red iyr:2012 eyr:2030 byr:1980 hcl:#623a2f"))

        assertFalse(isPassportDataValid("pid:087499704 hgt:74in ecl:grn iyr:2009 eyr:2030 byr:1980 hcl:#623a2f"))
        assertFalse(isPassportDataValid("pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2031 byr:1980 hcl:#623a2f"))
        assertFalse(isPassportDataValid("pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1919 hcl:#623a2f"))

        assertFalse(isPassportDataValid("pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2g"))
    }

    @Test
    fun testCountValidPassports2() {
        assertEquals(4, countValidPassports(input2File, ::isPassportDataValid))
        assertEquals(2, countValidPassports(input0File, ::isPassportDataValid))
        assertEquals(123, countValidPassports(inputFile, ::isPassportDataValid))
    }
}