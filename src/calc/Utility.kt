package calc

import java.math.BigInteger

/**
 * Essential math utility functions
 */

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
