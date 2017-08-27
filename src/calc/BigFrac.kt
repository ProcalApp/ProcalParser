package calc

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

/**
 * BigFrac Class
 *
 * A class using BigDecimal to store a fraction unit,
 * in a form of
 * a/b
 *
 * By convention the negative sign is kept in numerator
 */
class BigFrac(numer: BigInteger = BigInteger.ZERO,
              denom: BigInteger = BigInteger.ONE) {
    private var numer: BigInteger = BigInteger.ZERO
    private var denom: BigInteger = BigInteger.ONE

    init {
        if (numer.compareTo(BigInteger.ZERO) == 0 && denom.compareTo(BigInteger.ZERO) == 0) {
            throw ArithmeticException("Cannot 0 div 0")
        }
        if (denom < BigInteger.ZERO) {
            this.numer = numer.negate()
            this.denom = denom.negate()
        } else {
            this.numer = numer
            this.denom = denom
        }
        var cf: BigInteger = Utility.findHCF(this.numer, this.denom)
        this.numer = this.numer.divide(cf)
        this.denom = this.denom.divide(cf)
    }

    constructor(numer: Long, denom: Long) : this(BigInteger.valueOf(numer), BigInteger.valueOf(denom))

    //TODO: Construct BigFrac from BigDecimal
    constructor(dec: BigDecimal) : this() {
    }

    fun simplify(): BigFrac {
        var cf: BigInteger = Utility.findHCF(this.numer, this.denom)
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

    //TODO: Find a way to throw exception if expn is too large to be Int
    fun pow(expn: Int): BigFrac {
        return BigFrac(this.numer.pow(expn), this.denom.pow(expn))
    }

    fun toDecimal(): BigDecimal {
        return try {
            BigDecimal(this.numer).divide(BigDecimal(this.denom))
        } catch (e: ArithmeticException) {
            BigDecimal(this.numer).divide(BigDecimal(this.denom), 20, RoundingMode.HALF_UP)
        }
    }

    fun isInt(): Boolean {
        return this.denom.compareTo(BigInteger.ONE) == 0
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
            true -> this.numer.toString()
            false -> this.numer.toString() + " / " + this.denom.toString()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as BigFrac
        if (this.numer == other.numer && this.denom == other.denom) return true
        return false
    }

    fun getNumer(): BigInteger {
        return this.numer
    }

    fun getDenom(): BigInteger {
        return this.denom
    }

    companion object {
        val ONE = BigFrac(1,1)
        val ZERO = BigFrac(0,1)
    }

}
