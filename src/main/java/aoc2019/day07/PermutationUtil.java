package aoc2019.day07;

import java.util.ArrayList;

class PermutationUtil {

    static ArrayList<String> generatePermutations(String array) {
        ArrayList<String> permutations = new ArrayList<>();
        if (array.length() == 0) {
            return permutations;
        }

        permutations(array.toCharArray(), 0, array.length(), permutations);
        return permutations;
    }

    private static void permutations(char[] arr, int loc, int len, ArrayList<String> result) {
        if (loc == len) {
            result.add(new String(arr));
            return;
        }

        // Pick the element to put at arr[loc]
        permutations(arr, loc + 1, len, result);
        for (int i = loc + 1; i < len; i++) {
            // Swap the current arr[loc] to position i
            swap(arr, loc, i);
            permutations(arr, loc + 1, len, result);
            // Restore the status of arr to perform the next pick
            swap(arr, loc, i);
        }
    }

    private static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
