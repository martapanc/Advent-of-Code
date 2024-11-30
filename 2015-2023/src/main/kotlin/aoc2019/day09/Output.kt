package aoc2019.day09

import lombok.Data

@Data
class Output {
    var code: String? = null
    var index = 0
    var relativeBase = 0

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

    constructor(code: String?, index: Long, relativeBase: Int) {
        this.code = code
        this.index = index.toInt()
        this.relativeBase = relativeBase
    }

    override fun toString(): String {
        return "{" +
                "code='" + code + '\'' +
                ", index=" + index +
                ", relativeBase=" + relativeBase +
                '}'
    }
}