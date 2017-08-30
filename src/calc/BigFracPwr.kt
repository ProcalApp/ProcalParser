package calc

import exceptions.InvalidException
import exceptions.ZeroPowerZeroException
import org.nevec.rjm.BigDecimalMath
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

/**
 * @class BigFracPwr Class
 *
 * @brief A class using BigDecimal to store a fraction power unit
 * @form  (p/q) * (a/b) ^ (m/n)
 */

/** @rule Default value 0 */
class BigFracPwr(prop: BigFrac = BigFrac.ONE,
                 base: BigFrac = BigFrac.ZERO,
                 expn: BigFrac = BigFrac.ONE) {

    private var prop: BigFrac
    private var base: BigFrac
    private var expn: BigFrac

    //TODO: Roots simplification & rationalization
    init {
        /** @rule No 0^0 */
        if (base == BigFrac.ZERO && expn == BigFrac.ZERO)
            throw ZeroPowerZeroException()
        /** @rule Uniform 0 definition: 1 * 0 ^ 1 */
        else if (prop == BigFrac.ZERO || base == BigFrac.ZERO) {
            this.prop = BigFrac.ONE
            this.base = BigFrac.ZERO
            this.expn = BigFrac.ONE
        }
        /** @rule Uniform 1 definition: 1 * 1 ^ 1 */
        else if (base == BigFrac.ONE) {
            this.prop = BigFrac.ONE
            this.base = prop
            this.expn = BigFrac.ONE
        }
        /** @rule Simplification of integer expn: 1 * (a / b) ^ 1 */
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
            /** @rule Uniform integer definition: 1 * n ^ 1 */
            else {
                this.prop = BigFrac.ONE
                this.base = prop
                this.expn = BigFrac.ONE
            }
        }
        /** @rule Simplification of negative expn: to positive indices */
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
        return (prop.toDecimal().setScale(25, RoundingMode.HALF_UP) *
                BigDecimalMath.pow(base.toDecimal().setScale(25, RoundingMode.HALF_UP),
                        expn.toDecimal().setScale(25, RoundingMode.HALF_UP))).setScale(25, RoundingMode.HALF_UP)
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
        /** @rule frac + frac -> frac */
        return if (this.isFrac() && rhs.isFrac())
            BigFracPwr(base = this.base + rhs.base)
        /** @rule addition of like terms -> change prop */
        else if (this.base == rhs.base && this.expn == rhs.expn)
            BigFracPwr(this.prop + rhs.prop, this.base, this.expn)
        /** @rule cast to BigDecimal if cannot be simplified */
        else this.toDecimal() + rhs.toDecimal()
    }

    operator fun minus(rhs: BigFracPwr): Any {
        return this + rhs.negate()
    }

    operator fun times(rhs: BigFracPwr): Any {
        /** @rule rhs expn == 1 -> multiply with prop */
        return if (rhs.expn == BigFrac.ONE)
            BigFracPwr(this.prop * rhs.base, this.base, this.expn)
        /** @rule same base -> indices law */
        else if (this.base == rhs.base)
            BigFracPwr(this.prop * rhs.prop, this.base, this.expn + rhs.expn)
        /** @rule inverse base -> indices law */
        else if (this.base == rhs.base.inverse())
            BigFracPwr(this.prop * rhs.prop, this.base, this.expn - rhs.expn)
        /** @rule same expn -> group base */
        else if (this.expn == rhs.expn)
            BigFracPwr(this.prop * rhs.prop, this.base * rhs.base, this.expn)
        /** @rule cast to BigDecimal if cannot be simplified */
        else (this.toDecimal() * rhs.toDecimal()).setScale(25, RoundingMode.HALF_UP)
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
        } else if (temp is BigFracPwr) {
            return if (temp.isPos()) 1 else 0
        } else throw InvalidException() //should not be invoked
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
