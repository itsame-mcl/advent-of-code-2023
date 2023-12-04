import kotlin.math.pow

data class Scratchcard(val winningNumbers: List<Int>, val cardNumbers: List<Int>) {
    fun getMatches(): Int {
        return cardNumbers.intersect(winningNumbers.toSet()).size
    }

    fun getScore(): Int {
        val matches = this.getMatches().toDouble()
        return when(matches) {
            0.0 -> 0
            else -> 2.0.pow(matches - 1).toInt()
        }
    }
}

fun main() {
    fun parseScratchcards(input: List<String>): List<Scratchcard> {
        val scratchcards = mutableListOf<Scratchcard>()
        for(line in input){
            val subStrings = line.split(":","|")
            scratchcards.add(Scratchcard(subStrings[1].trim().split(" ").filterNot { number -> number == "" }.map { number -> number.toInt() },
                subStrings[2].trim().split(" ").filterNot { number -> number == "" }.map { number -> number.toInt() }))
        }
        return scratchcards
    }

    fun part1(input: List<String>): Int {
        return parseScratchcards(input).fold(0) {acc, scratchcard -> acc + scratchcard.getScore() }
    }

    fun part2(input: List<String>): Int {
        val scratchcards = parseScratchcards(input)
        val cards = IntArray(scratchcards.size){1}
        for (i in scratchcards.indices){
            for(j in 1..scratchcards[i].getMatches()){
                cards[i+j] += cards[i]
            }
        }
        return cards.sum()
    }

    check(part1(readInput("day04_sample")) == 13)
    part1(readInput("day04_input")).println()
    check(part2(readInput("day04_sample")) == 30)
    part2(readInput("day04_input")).println()
}