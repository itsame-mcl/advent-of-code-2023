import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 * from : https://github.com/kotlin-hands-on/advent-of-code-kotlin-template/blob/main/src/Utils.kt
 */
fun readInput(name: String) = Path("src/main/resources/$name.txt").readLines()

/**
 * The cleaner shorthand for printing output.
 * from : https://github.com/kotlin-hands-on/advent-of-code-kotlin-template/blob/main/src/Utils.kt
 */
fun Any?.println() = println(this)

// from : https://stackoverflow.com/questions/62189457/get-indexes-of-substrings-contained-in-a-string-in-kotlin-way
fun String?.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> {
    return this?.let {
        val regex = if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr)
        regex.findAll(this).map { it.range.start }.toList()
    } ?: emptyList()
}