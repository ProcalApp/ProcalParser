package calc

import exceptions.ZeroPowerZeroException
import org.nevec.rjm.BigDecimalMath
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

/**
 * Essential math utility functions
 */
object Utility {
    val PI = BigDecimal("3.141592653589793238462643383279502884197")
    val LN10 = BigDecimal("2.30258509299404568401799145468436420760110148862877")
    val TWO = BigDecimal(2)

    val SCALE = Pair(25, RoundingMode.HALF_UP)


    /** Find HCF of two BigInteger */
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

    /** Check if the value of BigInteger can fit in an Int */
    fun isInt(bigInt: BigInteger): Boolean {
        return bigInt.toInt().toLong() == bigInt.toLong()
    }


}

/** Override original BigDecimal.setScale method */
fun BigDecimal.setScale(pair: Pair<Int, RoundingMode>): BigDecimal {
    return setScale(pair.first, pair.second)
}

/** Override original BigDecimalMath .pow() method */
fun BigDecimal.pow(rhs: BigDecimal): BigDecimal {
    if (this >= BigDecimal.ZERO) {
        if (rhs.compareTo(BigDecimal.ZERO) == 0) {
            throw ZeroPowerZeroException()
        }
        return BigDecimalMath.pow(this, rhs)
    } else {
        if (rhs.scale() == 0 && rhs.remainder(BigDecimal(2)).compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimalMath.pow(this.negate(), rhs)
        } else if (rhs.scale() == 0 && Math.abs(this.remainder(BigDecimal(2)).compareTo(BigDecimal.ZERO)) == 1) {
            return BigDecimalMath.pow(this.negate(), rhs).negate()
        } else {
            val rgtFrac = BigFrac(rhs)
            return if (rgtFrac.getDenom().remainder(BigInteger.valueOf(2)) == BigInteger.ZERO) {
                throw ArithmeticException("Cannot take negative even root.") //TODO: Complex Decimal
            } else {
                if (rgtFrac.getNumer().remainder(BigInteger.valueOf(2)) == BigInteger.ZERO) {
                    BigDecimalMath.pow(this.negate(), rhs)
                } else {
                    BigDecimalMath.pow(this.negate(), rhs).negate()
                }
            }
        }
    }
}