package calc

import exceptions.ZeroPowerZeroException
import java.math.BigDecimal
import java.math.BigInteger

/**
 * BigFracPwr Class
 *
 * A class using BigDecimal to store a fraction power unit
 * Form: (p/q) * (a/b) ^ (m/n)
 * Domain: Real only, fails for negative base & fraction expn
 */
/* TODO: Roots simplification */
/* TODO: Rationalization */
/* TODO: Handling complex cases (probably need BigReal to achieve)
 *    |- Throw exceptions for negative base & fraction expn with even denominator
 *    |- Disallow root multiplication (e.g. sqrt(-2) * sqrt(-2) = -2 not 2)
 */

/** Rule: Default value 0 */
open class BigFracPwr(prop: BigFrac = BigFrac.ONE,
                      base: BigFrac = BigFrac.ZERO,
                      expn: BigFrac = BigFrac.ONE) {

    private var prop: BigFrac
    private var base: BigFrac
    private var expn: BigFrac

    init {
        /** Rule: No 0^0 */
        if (base == BigFrac.ZERO && expn == BigFrac.ZERO)
            throw ZeroPowerZeroException()
        /** Rule: Uniform 0 definition: 1 * 0 ^ 1 */
        else if (prop == BigFrac.ZERO || base == BigFrac.ZERO) {
            this.prop = BigFrac.ONE
            this.base = BigFrac.ZERO
            this.expn = BigFrac.ONE
        }
        /** Rule: Uniform 1 definition: 1 * 1 ^ 1 */
        else if (base == BigFrac.ONE) {
            this.prop = BigFrac.ONE
            this.base = prop
            this.expn = BigFrac.ONE
        }
        /** Rule: Simplification of integer expn: 1 * (a / b) ^ 1 */
        else if (expn.isInt()) {
            if (expn.isPos()) {
                this.prop = BigFrac.ONE
                this.base = prop * base.pow(expn.getInt())
                this.expn = BigFrac.ONE
            } else if (expn.isNeg()) {
                this.prop = BigFrac.ONE
                this.base = prop * base.pow(-expn.getInt())
                this.expn = BigFrac.ONE
            }
            /** Rule: Uniform integer definition: 1 * n ^ 1 */
            else {
                this.prop = BigFrac.ONE
                this.base = prop
                this.expn = BigFrac.ONE
            }
        }
        /** Rule: Simplification of negative expn: to positive indices */
        else if (expn.isNeg()) {
            this.prop = prop
            this.base = base.inverse()
            this.expn = expn.negate()
        } else {
            this.prop = prop
            this.base = base
            this.expn = expn
        }
    }

    constructor(propNumer: Long, propDenom: Long, baseNumer: Long, baseDenom: Long, expnNumer: Long, expnDenom: Long) :
            this(BigFrac(propNumer, propDenom), BigFrac(baseNumer, baseDenom), BigFrac(expnNumer, expnDenom))

    fun toDecimal(): BigDecimal {
        return (prop.toDecimal().setScale(Utility.SCALE) *
                base.toDecimal().setScale(Utility.SCALE)
                        .pow(expn.toDecimal().setScale(Utility.SCALE))).setScale(Utility.SCALE)
    }

    fun isZero(): Boolean {
        return this.base == BigFrac.ZERO
    }

    fun isInt(): Boolean {
        return this.base.isInt() && this.expn == BigFrac.ONE || this.isZero()
    }

    fun isFrac(): Boolean {
        return this.prop == BigFrac.ONE && this.expn == BigFrac.ONE
    }

    fun negate(): BigFracPwr {
        return BigFracPwr(this.prop.negate(), this.base, this.expn)
    }

    fun inverse(): BigFracPwr {
        return BigFracPwr(this.prop.inverse(), this.base.inverse(), this.expn)
    }

    fun isPos(): Boolean {
        return this.prop.getNumer() > BigInteger.ZERO
    }

    fun isNeg(): Boolean {
        return this.prop.getNumer() < BigInteger.ZERO
    }

    operator fun plus(rhs: BigFracPwr): Any {
        /** Rule: frac + frac -> frac */
        return if (this.isFrac() && rhs.isFrac())
            BigFracPwr(base = this.base + rhs.base)
        /** Rule: addition of like terms -> change prop */
        else if (this.base == rhs.base && this.expn == rhs.expn)
            BigFracPwr(this.prop + rhs.prop, this.base, this.expn)
        /** Rule: cast to BigDecimal if cannot be simplified */
        else this.toDecimal() + rhs.toDecimal()
    }

    operator fun minus(rhs: BigFracPwr): Any {
        return this + rhs.negate()
    }

    operator fun times(rhs: BigFracPwr): Any {
        /** Rule: rhs expn == 1 -> multiply with prop */
        return if (rhs.expn == BigFrac.ONE)
            BigFracPwr(this.prop * rhs.base, this.base, this.expn)
        /** Rule: same base -> indices law */
        else if (this.base == rhs.base)
            BigFracPwr(this.prop * rhs.prop, this.base, this.expn + rhs.expn)
        /** Rule: inverse base -> indices law */
        else if (this.base == rhs.base.inverse())
            BigFracPwr(this.prop * rhs.prop, this.base, this.expn - rhs.expn)
        /** Rule: same expn -> group base */
        else if (this.expn == rhs.expn)
            BigFracPwr(this.prop * rhs.prop, this.base * rhs.base, this.expn)
        /** Rule: cast to BigDecimal if cannot be simplified */
        else (this.toDecimal() * rhs.toDecimal()).setScale(Utility.SCALE)
    }

    operator fun div(rhs: BigFracPwr): Any {
        return this * rhs.inverse()
    }

    operator fun unaryMinus(): BigFracPwr {
        return this.negate()
    }

    operator fun compareTo(other: BigFracPwr): Int {
        if (this == other) return 0
        val temp = this - other
        if (temp is BigDecimal) {
            return temp.compareTo(BigDecimal.ZERO)
        } else { // temp is BigFracPwr
            temp as BigFracPwr
            return if (temp.isPos()) 1 else 0
        }
    }


    override fun toString(): String {
        return if (this.isFrac()) {
            this.base.toString()
        } else if (this.prop == BigFrac.ONE) {
            this.base.toString() + " ^ " + this.expn.toString()
        } else this.prop.toString() + " * " + this.base.toString() + " ^ " + this.expn.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as BigFracPwr
        if (this.prop == other.prop && this.base == other.base && this.expn == other.expn) return true
        return false
    }

    override fun hashCode(): Int {
        return this.prop.hashCode() * 31 * 31 +
                this.base.hashCode() * 31 +
                this.expn.hashCode()
    }

    companion object {
        val ONE = BigFracPwr(base = BigFrac.ONE)
        val ZERO = BigFracPwr()
    }

}
