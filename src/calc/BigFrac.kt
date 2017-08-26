package calc

import java.math.BigDecimal

/**
 * BigFraction Class
 *
 * A class using BigDecimal to store a fraction unit,
 * in a form of
 * a/b
 *
 * By convention the negative sign is kept in numerator
 */

class BigFrac(numer: BigDecimal = BigDecimal.ZERO,
               denom: BigDecimal = BigDecimal.ONE) {
    private var numer: BigDecimal = BigDecimal(0)
    private var denom: BigDecimal = BigDecimal(1)

    init {
        if (this.denom.compareTo(BigDecimal.ZERO) < 0) {
            this.numer = numer.negate()
            this.denom = denom.negate()
        } else {
            this.numer = numer
            this.denom = denom
        }
    }

    constructor(dec: BigDecimal) : this() {
        // TBC
    }

    fun simplify(): BigFrac {
        var cf: BigDecimal = findHCF(this.numer, this.denom)
        return BigFrac(numer.divide(cf), denom.divide(cf))
    }
    fun negate(): BigFrac {
        return BigFrac(this.numer.negate(), this.denom)
    }
    fun inverse(): BigFrac {
        if (this.numer.compareTo(BigDecimal.ZERO) > 0) {
            return BigFrac(this.denom, this.numer)
        } else if (this.numer.compareTo(BigDecimal.ZERO) < 0) {
            return BigFrac(this.denom.negate(), this.numer.negate())
        } else {
            throw ArithmeticException("Cannot inverse 0 fraction")
        }
    }

    operator fun plus(rhs: BigFrac): BigFrac {
        return BigFrac(this.numer*rhs.denom + rhs.numer*this.denom,
                this.denom*rhs.denom).simplify()
    }

    operator fun minus(rhs: BigFrac): BigFrac {
        return this + rhs.negate()
    }

    operator fun times(rhs: BigFrac): BigFrac {
        return BigFrac(this.numer*rhs.numer, this.denom*rhs.denom).simplify()
    }

    operator fun div(rhs: BigFrac): BigFrac {
        return this * rhs.inverse()
    }

    fun toDecimal(): BigDecimal {
        return this.numer / this.denom
    }

    fun isInt(): Boolean {
        return this.denom.compareTo(BigDecimal.ONE) == 0
    }

    fun isZero(): Boolean {
        return this.numer.compareTo(BigDecimal.ZERO) == 0 && this.denom.compareTo(BigDecimal.ZERO) == 0
    }

    override fun toString(): String {
        return when (this.isInt()){
            true -> this.numer.toString()
            false -> this.numer.toString() + " / " + this.denom.toString()
        }
    }

}
