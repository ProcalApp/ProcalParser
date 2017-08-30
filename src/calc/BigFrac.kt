package calc

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

/**
 * @class BigFrac Class
 *
 * @brief A class using BigDecimal to store a fraction unit
 * @form a/b
 */

/** @rule Default value 0 */
class BigFrac(numer: BigInteger = BigInteger.ZERO,
              denom: BigInteger = BigInteger.ONE) {
    private var numer: BigInteger
    private var denom: BigInteger

    init {
        /** @rule No n/0 */
        if (denom.compareTo(BigInteger.ZERO) == 0) {
            throw ArithmeticException("Cannot divide by zero")
        }
        /** @rule Negative sign always kept in numerator */
        if (denom < BigInteger.ZERO) {
            this.numer = numer.negate()
            this.denom = denom.negate()
        } else {
            this.numer = numer
            this.denom = denom
        }
        /** @rule Always in simplest form */
        val cf: BigInteger = Utility.findHCF(this.numer, this.denom)
        this.numer = this.numer.divide(cf)
        this.denom = this.denom.divide(cf)
    }

    constructor(numer: Long, denom: Long) : this(BigInteger.valueOf(numer), BigInteger.valueOf(denom))
    // Copy constructor
    constructor(copy: BigFrac) : this(copy.numer, copy.denom)

    constructor(dec: BigDecimal) : this(DecToFrac.dectofrac(dec))


    //should not be used since fractions are simplified in constructors
    private fun simplify(): BigFrac {
        val cf: BigInteger = Utility.findHCF(this.numer, this.denom)
        return BigFrac(numer.divide(cf), denom.divide(cf))
    }

    fun negate(): BigFrac {
        return BigFrac(this.numer.negate(), this.denom)
    }

    fun inverse(): BigFrac {
        if (this.numer > BigInteger.ZERO) {
            return BigFrac(this.denom, this.numer)
        } else if (this.numer.compareTo(BigInteger.ZERO) < 0) {
            return BigFrac(this.denom.negate(), this.numer.negate())
        } else {
            throw ArithmeticException("Cannot inverse 0 fraction")
        }
    }

    operator fun plus(rhs: BigFrac): BigFrac {
        return BigFrac(this.numer * rhs.denom + rhs.numer * this.denom,
                this.denom * rhs.denom)
    }

    operator fun minus(rhs: BigFrac): BigFrac {
        return this + rhs.negate()
    }

    operator fun times(rhs: BigFrac): BigFrac {
        return BigFrac(this.numer * rhs.numer, this.denom * rhs.denom)
    }

    operator fun div(rhs: BigFrac): BigFrac {
        return this * rhs.inverse()
    }

    operator fun unaryMinus(): BigFrac {
        return this.negate()
    }

    fun pow(expn: Int): BigFrac {
        return BigFrac(this.numer.pow(expn), this.denom.pow(expn))
    }

    fun toDecimal(): BigDecimal {
        return try {
            BigDecimal(this.numer).divide(BigDecimal(this.denom))
        } catch (e: ArithmeticException) {
            BigDecimal(this.numer).divide(BigDecimal(this.denom), 25, RoundingMode.HALF_UP)
        }
    }

    fun isInt(): Boolean {
        return this.denom.compareTo(BigInteger.ONE) == 0 && Utility.isInt(this.numer)
    }

    fun isEvenInt(): Boolean {
        return this.isInt() && (this.numer.mod(BigInteger.valueOf(2)).toInt() == 0)
    }

    fun isOddInt(): Boolean {
        return this.isInt() && (this.numer.mod(BigInteger.valueOf(2)).toInt() == 1)
    }

    fun isZero(): Boolean {
        return this.numer.compareTo(BigInteger.ZERO) == 0
    }

    fun isPos(): Boolean {
        return this.numer > BigInteger.ZERO
    }

    fun isNeg(): Boolean {
        return this.numer < BigInteger.ZERO
    }

    override fun toString(): String {
        return when (this.isInt()) {
        /** @rule Always omit denominator of 1 */
            true -> this.numer.toString()
            false -> when (this.isNeg()) {
            /** @rule Negative sign outside bracket */
                true -> "-( " + this.numer.negate().toString() + " / " + this.denom.toString() + " )"
                false -> "( " + this.numer.toString() + " / " + this.denom.toString() + " )"
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as BigFrac
        if (this.numer == other.numer && this.denom == other.denom) return true
        return false
    }

    override fun hashCode(): Int {
        return this.numer.hashCode() * 31 + this.denom.hashCode()
    }

    fun getNumer(): BigInteger {
        return this.numer
    }

    fun getDenom(): BigInteger {
        return this.denom
    }

    fun getInt(): Int {
        return if (this.isInt()) {
            this.numer.toInt()
        } else {
            throw ArithmeticException("Trying to invoke getInt() for non-Int BigFrac") // should not be invoked
        }
    }

    companion object {
        val ONE = BigFrac(numer = BigInteger.ONE)
        val ZERO = BigFrac()
    }

}
