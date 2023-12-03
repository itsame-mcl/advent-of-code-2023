    data class SchemaSymbol(val symbolType: String, val lineIndex: Int, val colIndex: Int) {
        fun getGearRatio(numbers: List<SchemaNumber>): Int{
            return if (symbolType != "*") 0 else {
                val neighborhood = numbers.filter { number -> ((this.lineIndex == number.lineIndex) and (this.colIndex in listOf(number.colRange.first-1, number.colRange.last+1))) or
                        ((this.lineIndex in listOf(number.lineIndex-1, number.lineIndex+1)) and (IntRange(number.colRange.first -1, number.colRange.last+1).contains(this.colIndex))) }
                if (neighborhood.size != 2) 0 else {
                    neighborhood.fold(1) {prod: Int, neighbor: SchemaNumber -> prod * neighbor.value}
                }
            }
        }
    }
    data class SchemaNumber(val value: Int, val lineIndex: Int, val colRange: IntRange) {
        fun isPartNumber(symbols: List<SchemaSymbol>): Boolean {
            return symbols.any { symbol -> ((symbol.lineIndex == this.lineIndex) and (symbol.colIndex in listOf(this.colRange.first-1, this.colRange.last+1))) or
                                           ((symbol.lineIndex in listOf(this.lineIndex-1, this.lineIndex+1)) and (IntRange(this.colRange.first -1, this.colRange.last+1).contains(symbol.colIndex)))}
        }

        fun getValueIfIsPartNumber(symbols: List<SchemaSymbol>): Int {
            return if (this.isPartNumber(symbols)) this.value else 0
        }
    }

fun main() {

    fun parseSchematic(input: List<String>): Pair<List<SchemaNumber>, List<SchemaSymbol>> {
        val schemaNumbers = mutableListOf<SchemaNumber>()
        val schemaSymbols = mutableListOf<SchemaSymbol>()
        val numbersRegex = Regex("\\d+")
        val symbolsRegex = Regex("[\\D&&[^.\\s]]")
        var lineIndex = 0
        for (line in input){
            for (number in numbersRegex.findAll(line)) {
                schemaNumbers.add(SchemaNumber(number.value.toInt(), lineIndex, number.range))
            }
            for (symbol in symbolsRegex.findAll(line)) {
                schemaSymbols.add(SchemaSymbol(symbol.value, lineIndex, symbol.range.first))
            }
            lineIndex += 1
        }
        return Pair(schemaNumbers, schemaSymbols)
    }

    fun part1(input: List<String>): Int {
        val (numbers, symbols) = parseSchematic(input)
        return numbers.fold(0) { acc: Int, number: SchemaNumber -> acc + number.getValueIfIsPartNumber(symbols) }
    }

    fun part2(input: List<String>): Int {
        val (numbers, symbols) = parseSchematic(input)
        return symbols.fold(0){acc: Int, symbol: SchemaSymbol -> acc + symbol.getGearRatio(numbers) }
    }

    check(part1(readInput("day03_sample")) == 4361)
    part1(readInput("day03_input")).println()

    check(part2(readInput("day03_sample")) == 467835)
    part2(readInput("day03_input")).println()
}