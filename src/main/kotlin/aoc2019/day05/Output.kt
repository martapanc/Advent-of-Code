package aoc2019.day05

class Output {
    var code: String? = null
    var index = 0

    constructor(code: String?) {
        this.code = code
    }

    constructor(index: Int) {
        this.index = index
    }

    constructor(code: String?, index: Int) {
        this.code = code
        this.index = index
    }

    constructor(code: String?, index: Long) {
        this.code = code
        this.index = index.toInt()
    }
}