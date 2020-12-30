package aoc2020.day18

fun runOperations(inputList: List<String>, order: OperationOrder): Long {
    val operationOrderFunction = if (order == OperationOrder.LEFT_TO_RIGHT) ::computeLeftToRight else ::computeAdditionBeforeMultiplication
    var totalSum = 0L
    for (operation in inputList) {
        val stack = ArrayDeque<String>()
        for (char in operation.split(" ")) {
            if (char == ")") {
                var lastElem = stack.last()
                var bracketContent = ""
                while (lastElem != "(") {
                    bracketContent = "$lastElem $bracketContent"
                    stack.removeLast()
                    lastElem = stack.last()
                }
                stack.removeLast()
                stack.add(operationOrderFunction(bracketContent.trim()).toString())
            } else {
                stack.add(char)
            }
        }
        totalSum += operationOrderFunction(operationStackToString(stack))
    }
    return totalSum
}

fun computeLeftToRight(operation: String): Long {
    var accumulator = 0L
    val stack = ArrayDeque<String>()
    val split = operation.split(" ")
    var i = 0
    while (i < split.size) {
        val current = split[i]
        if (current == "+" || current == "*") {
            val prevNumber = stack.last().toLong()
            when (current) {
                "+" -> accumulator = prevNumber + split[i + 1].toLong()
                "*" -> accumulator = prevNumber * split[i + 1].toLong()
            }
            stack.removeLast()
            stack.add(accumulator.toString())
            i++
        } else stack.add(current)
        i++
    }
    return accumulator
}

fun computeAdditionBeforeMultiplication(operation: String): Long {
    var accumulator = 0L
    var stack = ArrayDeque<String>()
    var split = operation.split(" ")
    var i = 0
    if (operation.contains("+"))
        while (i < split.size) {
            val current = split[i]
            if (current == "+") {
                val prevNumber = stack.last().toLong()
                accumulator = prevNumber + split[i + 1].toLong()
                stack.removeLast()
                stack.add(accumulator.toString())
                i++
            } else stack.add(current)
            i++
        }

    if (operation.contains("*")) {
        var reducedOperation = operation
        if (stack.size != 0) {
            reducedOperation = operationStackToString(stack)
            stack = ArrayDeque()
        }
        split = reducedOperation.split(" ")
        i = 0
        while (i < split.size) {
            val current = split[i]
            if (current == "*") {
                val prevNumber = stack.last().toLong()
                accumulator = prevNumber * split[i + 1].toLong()
                stack.removeLast()
                stack.add(accumulator.toString())
                i++
            } else stack.add(current)
            i++
        }
    }
    return accumulator
}

fun operationStackToString(stack: ArrayDeque<String>): String {
    var string = ""
    stack.forEach { elem -> string += "$elem " }
    return string.trim()
}

enum class OperationOrder { LEFT_TO_RIGHT, ADDITION_BEFORE_MULTIPLICATION }