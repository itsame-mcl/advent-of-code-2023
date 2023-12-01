fun main() {
    fun String.computeCalibration(withTextDigits: Boolean = false): Int {
        val digits = CharArray(this.length)
        val patterns: Map<String, Char> = mapOf("zero" to '0', "one" to '1', "two" to '2', "three" to '3', "four" to '4', "five" to '5',
            "six" to '6', "seven" to '7', "eight" to '8', "nine" to '9')
        patterns.forEach { pattern -> this.indexesOf(pattern.value.toString()).forEach{index -> digits[index] = pattern.value}}
        if (withTextDigits) patterns.forEach { pattern -> this.indexesOf(pattern.key).forEach{index -> digits[index] = pattern.value}}
        return try { buildString { append(digits.first { char -> char != '\u0000' }).append(digits.last { char -> char != '\u0000' }) }.toInt() } catch (e: NoSuchElementException) {0}
    }

    fun part1(input: List<String>): Int {
        return input.fold(0) {acc, line -> acc + line.computeCalibration(false)}
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) {acc, line -> acc + line.computeCalibration(true)}
    }

    check(part1(readInput("day01_sample")) == 142)
    part1(readInput("day01_input")).println()

    check(part2(readInput("day01_sample_part2")) == 281)
    part2(readInput("day01_input")).println()
}