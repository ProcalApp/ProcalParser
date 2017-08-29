package calc

import java.math.BigInteger

/**
 * Essential math utility functions
 */
object Utility {
    /** @brief Find HCF of two BigInteger */
    fun findHCF(m: BigInteger, n: BigInteger): BigInteger {
        var a: BigInteger = m
        var b: BigInteger = n
        var c: BigInteger = BigInteger.ONE
        if (a.compareTo(b) == -1) {
            c = a; a = b; b = c
        }
        while (c.compareTo(BigInteger.ZERO) != 0) {
            c = a.remainder(b); a = b; b = c;
        }
        return a.abs()
    }

    /** @brief Check if the value of BigInteger can fit in an Int */
    fun isInt(bigInt: BigInteger): Boolean {
        return bigInt.toInt().toLong() == bigInt.toLong()
    }
}

