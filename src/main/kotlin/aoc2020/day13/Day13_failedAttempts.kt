package aoc2020.day13


// 1068781 = 152682 * 7
// 1068782 = 82214 * 13
// 1068785 = 18115 * 59
fun findEarliestTimestamp(inputList: List<Int>, min: Int, max: Int): Long {
    val listOfMultiplesOfFirstNumber = mutableListOf<Long>()
    val listOfMultiplesOfSecondNumber = mutableListOf<Long>()
    val listOfMultiplesOfThirdNumber = mutableListOf<Long>()
    val firstNum = inputList[0]
    val secondNum = inputList[1]
    val thirdNum = inputList[4]
    val fourthNum = inputList[6]
    val fifthNum = inputList[7]
    for (i in min..max) {
        listOfMultiplesOfFirstNumber.add((firstNum * i).toLong())
        listOfMultiplesOfSecondNumber.add((secondNum * i).toLong())
        listOfMultiplesOfThirdNumber.add((thirdNum * i).toLong())
    }

    val candidates = mutableListOf<Long>()
    for (first in listOfMultiplesOfFirstNumber) {
        if (listOfMultiplesOfSecondNumber.contains(first + 1)
            && listOfMultiplesOfThirdNumber.contains(first + 4)
        ) {
            candidates.add(first)
        }
    }

    val reducedCandidates = mutableListOf<Long>()
    for (c in candidates) {
        if ((c + 6) % fourthNum == 0L && (c + 7) % fifthNum == 0L) {
            reducedCandidates.add(c)
        }
    }
    return candidates[0]
}

// This takes a lot - run at your own risk
fun findEarliestTimestamp2(inputList: List<Int>): Long {
    val indices = inputList.filter { it != -1 }.map { inputList.indexOf(it) }

    val candidates = mutableListOf<Long>()
    for (i in 2702702736321..27027027027027) {
        val product = inputList[indices[0]] * i
        if ((product + indices[1]) % inputList[indices[1]] == 0L
            && (product + indices[2]) % inputList[indices[2]] == 0L
            && (product + indices[3]) % inputList[indices[3]] == 0L
            && (product + indices[4]) % inputList[indices[4]] == 0L
            && (product + indices[5]) % inputList[indices[5]] == 0L
            && (product + indices[6]) % inputList[indices[6]] == 0L
            && (product + indices[7]) % inputList[indices[7]] == 0L
            && (product + indices[8]) % inputList[indices[8]] == 0L
        ) {
            candidates.add(product)
        }
    }
    return candidates[0]
}
