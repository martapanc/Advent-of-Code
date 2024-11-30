package aoc2018.day12;

import java.util.Arrays;

public class Day17Part1 {

    public static void main(String[] args){

        int[] inputs = new int[]{43, 3, 4, 10, 21, 44, 4, 6, 47, 41, 34, 17, 17, 44, 36, 31, 46, 9, 27, 38};
        Boolean[] booleans = new Boolean[20];
        int count = 0;

        while (Arrays.asList(booleans).contains(true)) {

            int total = 0;

            for (int i = 0; i < inputs.length; i++) {
                if (booleans[i]) {
                    total += inputs[i];
                }
            }

            if (total == 150) {
                count++;
            }

            for (int i = inputs.length - 1; i >= 0; i--) {
                if (booleans[i]) {
                    booleans[i] = false;
                    break;
                } else {
                    booleans[i] = true;
                }
            }
        }

        System.out.println(count);
    }
}