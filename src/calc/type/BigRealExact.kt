package calc.type

import calc.Utility
import calc.pow
import calc.setScale
import exceptions.ZeroPowerZeroException
import java.math.BigDecimal

/**
 * BigRealExact Class
 *
 * A class using BigDecimal to store a fraction power unit with pi
 * Form: (p/q) * (a/b) ^ (m/n) * pi^{0, 1}
 * Domain: Real only, fails for negative base & fraction expn
 */

/** Rule: Default value 0 */
class BigRealExact(private val bigFracPwr: BigFracPwr = BigFracPwr.ZERO,
                   private val hasPi: Boolean = false) {

    constructor(prop: BigFrac = BigFrac.ONE,
                base: BigFrac = BigFrac.ZERO,
                expn: BigFrac = BigFrac.ONE,
                hasPi: Boolean = false) : this(BigFracPwr(prop, base, expn), hasPi)

    fun toDecimal(): BigDecimal {
        return if (this.hasPi)
            (bigFracPwr.toDecimal() * Utility.PI.setScale(Utility.SCALE)).setScale(Utility.SCALE)
        else bigFracPwr.toDecimal()
    }

    fun evalaute(): BigCmplxDecimal {
        return if (this.hasPi)
            (bigFracPwr.evaluate() * BigCmplxDecimal(Utility.PI.setScale(Utility.SCALE))).setScale(Utility.SCALE)
        else bigFracPwr.evaluate()
    }

    fun negate(): BigRealExact {
        return BigRealExact(this.bigFracPwr.negate(), hasPi)
    }

    operator fun plus(rhs: BigRealExact): Any {
        if (this.hasPi != rhs.hasPi) {
            return this.toDecimal() + rhs.toDecimal()
        } else {
            val temp = this.bigFracPwr + rhs.bigFracPwr
            if (temp is BigFracPwr)
                return BigRealExact(temp, this.hasPi)
            else if (this.hasPi)
                return ((temp as BigDecimal) * Utility.PI.setScale(Utility.SCALE)).setScale(Utility.SCALE)
            else
                return temp as BigDecimal
        }
    }

    operator fun minus(rhs: BigRealExact): Any {
        return this + rhs.negate()
    }

    operator fun times(rhs: BigRealExact): Any {
        val temp = this.bigFracPwr * rhs.bigFracPwr
        if (temp is BigFracPwr && !(this.hasPi && rhs.hasPi)) {
            return BigRealExact(temp, this.hasPi xor rhs.hasPi)
        } else {
            return (this.toDecimal() * rhs.toDecimal()).setScale(Utility.SCALE)
        }
    }

    operator fun div(rhs: BigRealExact): Any {
        val temp = this.bigFracPwr / rhs.bigFracPwr
        if (temp is BigFracPwr && !(!this.hasPi && rhs.hasPi)) {
            return BigRealExact(temp, this.hasPi xor rhs.hasPi)
        } else {
            return (this.toDecimal() / rhs.toDecimal()).setScale(Utility.SCALE)
        }
    }

    fun pow(rhs: BigRealExact): Any {
        if (rhs == ONE)
            return this
        else if (this == ONE)
            return ONE
        else if (this == ZERO && rhs == ZERO)
            return ZeroPowerZeroException()
        else if (this == ZERO)
            return ZERO
        else if (rhs == ZERO)
            return ONE
        else
            /** Rule: both do not have pi may try exact pow */
            if (!this.hasPi && !rhs.hasPi) {
                val temp = this.pow(rhs)
                if (temp is BigFracPwr) {
                    return BigRealExact(bigFracPwr = temp)
                } else
                    return this.toDecimal().pow(rhs.toDecimal())
            } else
                return this.toDecimal().pow(rhs.toDecimal())
    }

    fun isZero(): Boolean {
        return this.bigFracPwr == BigFracPwr.ZERO
    }

    fun isInt(): Boolean {
        return this.bigFracPwr.isInt() && !this.hasPi
    }

    fun isFrac(): Boolean {
        return this.bigFracPwr.isFrac() && !this.hasPi
    }

    fun getProp(): BigFrac {
        return this.bigFracPwr.getProp()
    }

    fun getBase(): BigFrac {
        return this.bigFracPwr.getBase()
    }

    fun getExpn(): BigFrac {
        return this.bigFracPwr.getExpn()
    }

    override fun toString(): String {
        if (this.bigFracPwr == BigFracPwr.ONE) {
            return "pi"
        } else if (this.bigFracPwr == BigFracPwr.ZERO) {
            return "0"
        } else return this.bigFracPwr.toString() + if (this.hasPi) {
            " * pi"
        } else {
            ""
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as BigRealExact
        if (this.bigFracPwr == other.bigFracPwr && this.hasPi == other.hasPi) return true
        return false
    }

    override fun hashCode(): Int {
        return this.bigFracPwr.hashCode() * 31 +
                this.hasPi.hashCode()
    }

    companion object {
        val ONE = BigRealExact(BigFracPwr.ONE, false)
        val ZERO = BigRealExact()
        val PI = BigRealExact(BigFracPwr.ONE, true)
    }

}