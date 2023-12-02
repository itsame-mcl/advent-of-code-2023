fun main() {
    data class Subset(val red: Int, val green: Int, val blue: Int) {
        fun isPossible(avaliableRed: Int, avaliableGreen: Int, avaliableBlue: Int): Boolean {
            return (this.red <= avaliableRed) and (this.green <= avaliableGreen) and (this.blue <= avaliableBlue)
        }
    }

    data class Game(val id: Int, val subsets: List<Subset>) {
        fun isPossible(avaliableRed: Int, avaliableGreen: Int, avaliableBlue: Int): Boolean {
            return subsets.all { subset -> subset.isPossible(avaliableRed, avaliableGreen, avaliableBlue) }
        }

        fun getIdIfIsPossible(avaliableRed: Int, avaliableGreen: Int, avaliableBlue: Int): Int {
            return if (this.isPossible(avaliableRed, avaliableGreen, avaliableBlue)) this.id else 0
        }

        fun computePower(): Int {
            val redPower = this.subsets.maxOf { subset -> subset.red }
            val greenPower = this.subsets.maxOf { subset -> subset.green }
            val bluePower = this.subsets.maxOf { subset -> subset.blue }
            return redPower * greenPower * bluePower
        }
    }

    fun parseGame(input: String): Game {
        val redRegex = Regex("(\\d+) red")
        val greenRegex = Regex("(\\d+) green")
        val blueRegex = Regex("(\\d+) blue")
        val idRegex = Regex("Game (\\d+)")
        val subStrings = input.split(":",";")
        val id = idRegex.find(subStrings[0])?.groups?.get(1)?.value?.toInt() ?: 0
        val subsets = mutableListOf<Subset>()
        for (subString in subStrings.slice(1..subStrings.size-1)){
            val red = redRegex.find(subString)?.groups?.get(1)?.value?.toInt() ?: 0
            val green = greenRegex.find(subString)?.groups?.get(1)?.value?.toInt() ?: 0
            val blue = blueRegex.find(subString)?.groups?.get(1)?.value?.toInt() ?: 0
            subsets.add(Subset(red, green, blue))
        }
        return Game(id, subsets)
    }

    fun part1(input: List<String>): Int {
        return input.fold(0) {acc: Int, s: String -> acc + parseGame(s).getIdIfIsPossible(12,13,14) }
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) {acc: Int, s: String -> acc + parseGame(s).computePower() }
    }

    check(part1(readInput("day02_sample")) == 8)
    part1(readInput("day02_input")).println()

    check(part2(readInput("day02_sample")) == 2286)
    part2(readInput("day02_input")).println()
}