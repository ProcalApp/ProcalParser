package calc

import org.nevec.rjm.BigDecimalMath
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * BigFracPwr Class
 *
 * A class using BigDecimal to store a fraction power unit,
 * in a form of
 * (a/b) ^ (m/n)
 *
 * By convention the negative sign is kept in numerator
 */

class BigFracPwr(base: BigFrac = BigFrac(0, 1),
                 expn: BigFrac = BigFrac(1, 1)) {
    private var base = BigFrac(0, 1)
    private var expn = BigFrac(1, 1)

    init {
        this.base = base
        this.expn = expn
        if (this.base.isZero() && this.expn.isZero()) {
            throw ArithmeticException("Cannot have 0^0")
        } else if (this.base.isZero()) {
            this.expn = BigFrac(1, 1) // all to 0^1
        } else if (this.expn.isZero()) {
            this.base = BigFrac(1, 1) // all to 1^0
        } else if (this.expn.isInt() && this.expn.isPos()) {
            this.base = this.base.pow(this.expn.getNumer().toInt())
            this.expn = BigFrac(1, 1)  // all to (a/b)^1
        } else if (this.expn.isInt() && this.expn.isNeg()) {
            this.base = this.base.inverse().pow(this.expn.getNumer().toInt())
            this.expn = BigFrac(1, 1) // all to (a/b)^1
        }
    }

    constructor(baseNumer: Long, baseDenom: Long, expnNumer: Long, expnDenom: Long) : this(BigFrac(baseNumer, baseDenom), BigFrac(expnNumer, expnDenom))

    fun toDecimal(): BigDecimal {
        return BigDecimalMath.pow(base.toDecimal().setScale(20, RoundingMode.HALF_UP), expn.toDecimal().setScale(20, RoundingMode.HALF_UP))
    }

    fun inverse(): BigFracPwr {
        return BigFracPwr(this.base, this.expn.negate())
    }

    operator fun plus(rhs: BigFracPwr): BigDecimal {
        return this.toDecimal() + rhs.toDecimal()
    }

    operator fun minus(rhs: BigFracPwr): BigDecimal {
        return this.toDecimal() - rhs.toDecimal()
    }

    operator fun times(rhs: BigFracPwr): Any {
        return if (this.base != rhs.base && this.expn != rhs.expn && this.expn != rhs.expn.negate()) {
            (this.toDecimal() * rhs.toDecimal()).setScale(20, RoundingMode.HALF_UP)
        } else if (this.base == rhs.base && this.expn == rhs.expn) {
            BigFracPwr(this.base, this.expn + this.expn)
        } else if (this.base == rhs.base) {
            BigFracPwr(this.base, this.expn + rhs.expn)
        } else if (this.expn == rhs.expn) {
            BigFracPwr(this.base * rhs.base, this.expn)
        } else {
            BigFracPwr(this.base / rhs.base, this.expn)
        }
    }

    operator fun div(rhs: BigFracPwr): Any {
        return this * rhs.inverse()
    }

    override fun toString(): String {
        return "( " + this.base.toString() + " ) ^ ( " + this.expn.toString() + " )"
    }

    //TODO: Find a way to simplify something like (1/4)^(1/2)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as BigFracPwr
        if (this.base == other.base && this.expn == other.expn) return true
        return false
    }
}