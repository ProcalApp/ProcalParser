package calc

import java.math.BigDecimal
/**
 * @class BigPiFracPwr Class
 *
 * A class using BigDecimal to store a fraction power unit with pi
 * @form  (p/q) * (a/b) ^ (m/n) * pi^{0, 1}
 * @domain Complex for negative base and fraction expn
 */

/** @rule Default value 0 */
class BigPiFracPwr(bigFracPwr: BigFracPwr = BigFracPwr.ZERO,
                   hasPi: Boolean = false){
    var hasPi: Boolean = hasPi
    var bigFracPwr = bigFracPwr

    constructor(prop: BigFrac = BigFrac.ONE,
                base: BigFrac = BigFrac.ZERO,
                expn: BigFrac = BigFrac.ONE,
                hasPi: Boolean = false): this(BigFracPwr(prop,base,expn), hasPi)

    fun toDecimal(): BigDecimal {
        return if (this.hasPi)
            (bigFracPwr.toDecimal() * Utility.PI.setScale(Utility.SCALE)).setScale(Utility.SCALE)
            else bigFracPwr.toDecimal()
    }

    fun negate(): BigPiFracPwr {
        return BigPiFracPwr(this.bigFracPwr.negate(), hasPi)
    }

    operator fun plus(rhs: BigPiFracPwr): Any {
        if (this.hasPi != rhs.hasPi) {
            return this.toDecimal() + rhs.toDecimal()
        } else {
            val temp = this.bigFracPwr + rhs.bigFracPwr
            if (temp is BigFracPwr)
                return BigPiFracPwr(temp, this.hasPi)
            else if (this.hasPi)
                return ((temp as BigDecimal) * Utility.PI.setScale(Utility.SCALE)).setScale(Utility.SCALE)
            else
                return temp as BigDecimal
        }
    }

    operator fun minus(rhs: BigPiFracPwr): Any {
        return this + rhs.negate()
    }

    operator fun times(rhs: BigPiFracPwr): Any {
        val temp = this.bigFracPwr * rhs.bigFracPwr
        if (temp is BigFracPwr && !(this.hasPi && rhs.hasPi)) {
            return BigPiFracPwr(temp, this.hasPi xor rhs.hasPi)
        } else {
            return (this.toDecimal() * rhs.toDecimal()).setScale(Utility.SCALE)
        }
    }

    operator fun div(rhs: BigPiFracPwr): Any {
        val temp = this.bigFracPwr / rhs.bigFracPwr
        if (temp is BigFracPwr && !(!this.hasPi && rhs.hasPi)) {
            return BigPiFracPwr(temp, this.hasPi xor rhs.hasPi)
        } else {
            return (this.toDecimal() / rhs.toDecimal()).setScale(Utility.SCALE)
        }
    }

    override fun toString(): String {
        if (this.bigFracPwr == BigFracPwr.ONE) { return "pi" }
        else if (this.bigFracPwr == BigFracPwr.ZERO) { return "0" }
        else return this.bigFracPwr.toString() + if (this.hasPi) { " * pi" } else { "" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as BigPiFracPwr
        if (this.bigFracPwr == other.bigFracPwr && this.hasPi == other.hasPi) return true
        return false
    }

    override fun hashCode(): Int {
        return this.bigFracPwr.hashCode() * 31 +
                this.hasPi.hashCode()
    }

    companion object {
        val ONE = BigPiFracPwr(BigFracPwr.ONE,false)
        val ZERO = BigFracPwr()
        val PI = BigPiFracPwr(BigFracPwr.ONE, true)
    }

}