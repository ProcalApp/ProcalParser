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
                 expn: BigFrac = BigFrac(1, 1),
                 isPos: Boolean = true) {
    private var base = BigFrac(0, 1)
    private var expn = BigFrac(1, 1)
    private var isPos = true

    init {
        this.base = base
        this.expn = expn
        this.isPos = isPos
        if (this.base.isZero() && this.expn.isZero()) {
            throw ArithmeticException("Cannot have 0^0")
        } else if (this.base.isZero()) {
            this.expn = BigFrac.ONE // all to 0^1
            this.isPos = true
        } else if (this.expn.isZero()) {
            this.expn = BigFrac.ONE
            this.base = BigFrac.ONE // all to 1^1
        } else if (this.expn.isInt() && this.expn.isPos()) {
            this.base = this.base.pow(this.expn.getNumer().toInt())
            this.expn = BigFrac.ONE  // all to (a/b)^1
        } else if (this.expn.isInt() && this.expn.isNeg()) {
            this.base = this.base.inverse().pow(this.expn.negate().getNumer().toInt())
            this.expn = BigFrac.ONE// all to (a/b)^1
        }
        if (this.base.isNeg() && this.expn == BigFrac.ONE){
            this.base = this.base.negate()
            this.isPos = !this.isPos
        }
    }

    constructor(baseNumer: Long, baseDenom: Long, expnNumer: Long, expnDenom: Long, isPos: Boolean = true) : this(BigFrac(baseNumer, baseDenom), BigFrac(expnNumer, expnDenom), isPos)

    fun toDecimal(): BigDecimal {
        return BigDecimalMath.pow(base.toDecimal().setScale(20, RoundingMode.HALF_UP), expn.toDecimal().setScale(20, RoundingMode.HALF_UP))
    }

    fun inverse(): BigFracPwr {
        return BigFracPwr(this.base, this.expn.negate())
    }

    operator fun plus(rhs: BigFracPwr): Any {
        return if (this.isPos && rhs.isPos)
            if (this.expn == BigFrac.ONE && rhs.expn == BigFrac.ONE) {
                BigFracPwr(this.base+rhs.base, BigFrac.ONE)
            } else this.toDecimal() + rhs.toDecimal()
        else if (this.isPos && !rhs.isPos) {
            this - rhs.negate()
        } else if (!this.isPos && rhs.isPos) {
            rhs - this.negate()
        } else {
            var temp: Any = this.negate() + rhs.negate()
            if (temp is BigFracPwr) {
                return temp.negate()
            } else (temp as BigDecimal).negate()
        }
    }

    operator fun minus(rhs: BigFracPwr): Any {
        return if (this.isPos && rhs.isPos)
            if (this.expn == BigFrac.ONE && rhs.expn == BigFrac.ONE) {
                BigFracPwr(this.base-rhs.base, BigFrac.ONE)
            } else this.toDecimal() - rhs.toDecimal()
        else if (this.isPos && !rhs.isPos) {
            this + rhs.negate()
        } else if (!this.isPos && rhs.isPos) {
            var temp: Any = this.negate() + rhs.negate()
            if (temp is BigFracPwr) {
                return temp.negate()
            } else (temp as BigDecimal).negate()
        } else {
            rhs - this
        }
    }

    operator fun times(rhs: BigFracPwr): Any {
        var needNegate: Boolean = this.isPos xor rhs.isPos
        return if (needNegate) {
            if (this.base != rhs.base && this.expn != rhs.expn && this.expn != rhs.expn.negate()) {
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
        } else {
            if (this.base != rhs.base && this.expn != rhs.expn && this.expn != rhs.expn.negate()) {
                (this.toDecimal() * rhs.toDecimal()).setScale(20, RoundingMode.HALF_UP)
            } else if (this.base == rhs.base && this.expn == rhs.expn) {
                BigFracPwr(this.base, this.expn + this.expn)
            } else if (this.base == rhs.base) {
                BigFracPwr(this.base, this.expn + rhs.expn)
            } else if (this.expn == rhs.expn) {
                BigFracPwr(this.base * rhs.base, this.expn)
            } else {
                BigFracPwr(this.base / rhs.base, this.expn)
            }.negate()
        }
    }

    operator fun div(rhs: BigFracPwr): Any {
        return this * rhs.inverse()
    }

    fun isFrac(): Boolean {
        return this.expn == BigFrac.ONE
    }

    fun isInt(): Boolean {
        return this.expn == BigFrac.ONE && this.base.isInt()
    }

    fun isZero(): Boolean {
        return this.base.isZero()
    }

    fun isNeg(): Boolean {
        return !this.isPos
    }

    fun isPos(): Boolean {
        return this.isPos
    }

    fun negate(): BigFracPwr {
        return BigFracPwr(this.base, this.expn, !this.isPos)
    }

    override fun toString(): String {
        return if (this.isPos) { "" } else { "-" } +
                if (this.expn == BigFrac.ONE) { "" } else { "(" } +
                if (!this.base.isInt() && this.expn != BigFrac.ONE) { "( "} else { "" } +
                this.base.toString() +
                if (!this.base.isInt() && this.expn != BigFrac.ONE) { " )" } else { "" } +
                if (this.expn == BigFrac.ONE) { "" } else {
                    " ^ " +
                            if (!this.expn.isInt()) {
                                "( "
                            } else {
                                ""
                            } +
                            this.expn.toString() +
                            if (!this.expn.isInt()) {
                                " )"
                            } else {
                                ""
                            }
                } + if (this.isPos || this.expn == BigFrac.ONE) { "" } else { ")" }
    }

    //TODO: Find a way to simplify something like (1/4)^(1/2)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as BigFracPwr
        if (this.base == other.base && this.expn == other.expn) return true
        return false
    }

    companion object {
        val ONE = BigFracPwr(1,1,0,1)
        val ZERO = BigFracPwr(0,1,1,1)
    }
}