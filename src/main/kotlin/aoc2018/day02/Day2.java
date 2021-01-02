package aoc2018.day02;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2 {

    static int computeCheckSum(String[] idList) {
        int doubleCount = 0, tripleCount = 0;

        for (String idString : idList) {
            char[] idLetters = idString.toCharArray();
            Arrays.sort(idLetters);

            boolean doubleOnceCheck = false, tripleOnceCheck = false;
            List<Character> onceCheck = new ArrayList<>();

            for (char idLetter : idLetters) {
                if (!onceCheck.contains(idLetter)) {
                    onceCheck.add(idLetter);

                    int count = StringUtils.countMatches(idString, idLetter);

                    if (!doubleOnceCheck && count == 2) {
                        doubleOnceCheck = true;
                        doubleCount += 1;
                    }

                    if (!tripleOnceCheck && count == 3) {
                        tripleOnceCheck = true;
                        tripleCount += 1;
                    }

                    if (doubleOnceCheck && tripleOnceCheck) {
                        break;
                    }
                }
            }
        }

        return doubleCount * tripleCount;
    }
}
