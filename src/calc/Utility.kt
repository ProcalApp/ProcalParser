package calc

import java.math.BigDecimal

/**
 * Essential math utility functions
 */

fun findHCF(m: BigDecimal, n: BigDecimal): BigDecimal {
    var a: BigDecimal = m
    var b: BigDecimal = n
    var c: BigDecimal = BigDecimal.ONE
    if (a.compareTo(b) == -1) {
        c = a; a = b; b = c
    }
    while (c.compareTo(BigDecimal.ZERO) != 0) {
        c = a.remainder(b); a = b; b = c;
    }
    return a
}
