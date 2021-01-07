package aoc2019.day04;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Day4 {
    static int findPasswordCombinations(int min, int max) {
        return (int) IntStream.rangeClosed(min, max).filter(i -> isValidPassword(i + "")).count();
    }

    static int findPasswordCombinationsPart2(int min, int max) {
        return (int) IntStream.rangeClosed(min, max).filter(i -> isValidPasswordPart2(i + "")).count();
    }

    // Valid if there's at least one couple of two or more identical numbers
    static boolean isValidPassword(String input) {
        char[] chars = input.toCharArray();

        boolean theresACouple = false;
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] < chars[i - 1]) {
                return false;
            }
            if (chars[i] == chars[i - 1]) {
                theresACouple = true;
            }
        }
        return theresACouple;
    }

    // Valid if there's at least one couple of exactly two identical numbers (e.g. 112222 will succeed, 111222 will fail)
    static boolean isValidPasswordPart2(String input) {
        char[] chars = input.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] < chars[i - 1]) {
                return false;
            }
        }

        // Split input in groups of identical characters (e.g. "122444" -> [1, 22, 444])
        return Arrays.stream(input.split("(?<=(.))(?!\\1)")).anyMatch(groups -> groups.length() == 2);
    }
}
