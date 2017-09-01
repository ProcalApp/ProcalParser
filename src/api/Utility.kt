package api

/**
 * API Utility
 */
object Utility {
    fun fromUnicode(vararg unicodePoints: Int): String {
        var returnString = ""
        for (unicodePoint in unicodePoints) {
            returnString += Character.toString(unicodePoint.toChar())
        }
        return returnString
    }
}