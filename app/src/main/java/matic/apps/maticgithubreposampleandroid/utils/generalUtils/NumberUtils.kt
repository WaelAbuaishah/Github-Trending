package matic.apps.maticgithubreposampleandroid.utils.generalUtils

import java.util.*

object NumberUtils {


    fun formatNumberChars(value: Long): String {
        val suffixes = TreeMap<Long, String>()
        suffixes[1000L] = "k"
        suffixes[1000000L] = "M"
        suffixes[1000000000L] = "G"
        suffixes[1000000000000L] = "T"
        suffixes[1000000000000000L] = "P"
        suffixes[1000000000000000000L] = "E"

        try {
            if (value == java.lang.Long.MIN_VALUE) return formatNumberChars(java.lang.Long.MIN_VALUE + 1)
            if (value < 0) return "-" + formatNumberChars(-value)
            if (value < 1000) return java.lang.Long.toString(value)

            val e = suffixes.floorEntry(value)
            val divideBy = e.key
            val suffix = e.value

            val truncated = value / (divideBy!! / 10)
            val hasDecimal = truncated < 1000 && truncated / 100.0 != (truncated / 100).toDouble()
            return if (hasDecimal) (truncated / 10.0).toString() + suffix else (truncated / 10).toString() + suffix
        } catch (ex: Exception) {
            ex.printStackTrace()
            return value.toString() + ""
        }
    }
}