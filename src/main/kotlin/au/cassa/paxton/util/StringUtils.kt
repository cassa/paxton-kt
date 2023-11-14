package au.cassa.paxton.util

object StringUtils {

    const val TRUNCATED_STR_SUFFIX = "..."

    fun truncate(string: String, length: Int): String {
        if(length < TRUNCATED_STR_SUFFIX.length) {
            throw IllegalArgumentException("Lengh $length is less than the length required by the truncated suffix")
        }

        return if (string.length <= length) {
            string
        } else {
            string.substring(0, length - 1 - TRUNCATED_STR_SUFFIX.length) + TRUNCATED_STR_SUFFIX
        }
    }

}