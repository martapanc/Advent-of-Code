package aoc2019.day07

import java.util.ArrayList

internal object PermutationUtil {
//
//    @JvmStatic
//    fun generatePermutations(list: List<String>): ArrayList<List<String>> {
//        val permutations = ArrayList<List<String>>()
//        if (list.isEmpty()) {
//            return permutations
//        }
//        permutations(list, 0, list.size, permutations)
//        return permutations
//    }

    @JvmStatic
    fun generatePermutations(array: String): ArrayList<String> {
        val permutations = ArrayList<String>()
        if (array.isEmpty()) {
            return permutations
        }
        permutations(array.toCharArray(), 0, array.length, permutations)
        return permutations
    }

    private fun permutations(arr: CharArray, loc: Int, len: Int, result: ArrayList<String>) {
        if (loc == len) {
            result.add(String(arr))
            return
        }

        // Pick the element to put at arr[loc]
        permutations(arr, loc + 1, len, result)
        for (i in loc + 1 until len) {
            // Swap the current arr[loc] to position i
            swap(arr, loc, i)
            permutations(arr, loc + 1, len, result)
            // Restore the status of arr to perform the next pick
            swap(arr, loc, i)
        }
    }

//    private fun permutations(arr: List<String>, loc: Int, len: Int, result: ArrayList<List<String>>) {
//        if (loc == len) {
//            result.add(arr)
//            return
//        }
//
//        // Pick the element to put at arr[loc]
//        permutations(arr, loc + 1, len, result)
//        for (i in loc + 1 until len) {
//            // Swap the current arr[loc] to position i
//            swap(arr, loc, i)
//            permutations(arr, loc + 1, len, result)
//            // Restore the status of arr to perform the next pick
//            swap(arr, loc, i)
//        }
//    }

    private fun swap(arr: CharArray, i: Int, j: Int) {
        val tmp = arr[i]
        arr[i] = arr[j]
        arr[j] = tmp
    }
}