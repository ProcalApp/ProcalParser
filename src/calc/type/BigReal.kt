package calc.type

import calc.Utility
import calc.setScale
import exceptions.ComplexException
import exceptions.NullException
import java.math.BigDecimal
import java.math.BigInteger

/**
 * BigReal class
 * A container for either BigRealExact or BigDecimal
 * Form: (p/q) * (a/b) ^ (m/n) * pi^{0, 1} or x
 * Domain: Real
 */

class BigReal {
    private var exact: BigRealExact? = null
    private var decimal: BigDecimal? = null

    constructor(exact: BigRealExact) {
        this.exact = exact; this.decimal = null
        /** Tell if current value is complex */
        if (this.exact!!.getBase().isNeg() && this.exact!!.getExpn().getDenom().mod(BigInteger.valueOf(2)) == BigInteger.ZERO)
            throw ComplexException()
    }

    constructor(decimal: BigDecimal) {
        this.decimal = decimal; this.exact = null
    }

    fun negate(): BigReal {
        return if (this.exact != null)
            BigReal(this.exact!!.negate())
        else BigReal(this.decimal!!.negate())
    }

    fun getVal(): Any {
        return this.exact ?: this.decimal ?: throw NullException()
    }

    fun getDec(): BigDecimal {
        return this.exact?.toDecimal() ?: this.decimal ?: throw NullException()
    }

    fun hasExact(): Boolean {
        return this.exact != null
    }

    operator fun plus(rhs: BigReal): BigReal {
        return if (this.exact != null && rhs.exact != null){
            val temp = this.exact as BigRealExact + rhs.exact as BigRealExact
            if (temp is BigRealExact)
                BigReal(temp)
            else
                BigReal(temp as BigDecimal)
        } else {
            BigReal(this.getDec() + rhs.getDec())
        }
    }

    operator fun minus(rhs: BigReal): BigReal {
        return if (this.exact != null && rhs.exact != null){
            val temp = this.exact as BigRealExact - rhs.exact as BigRealExact
            if (temp is BigRealExact)
                BigReal(temp)
            else
                BigReal(temp as BigDecimal)
        } else {
            BigReal(this.getDec() - rhs.getDec())
        }
    }

    operator fun times(rhs: BigReal): BigReal {
        return if (this.exact != null && rhs.exact != null){
            val temp = this.exact as BigRealExact * rhs.exact as BigRealExact
            if (temp is BigRealExact)
                BigReal(temp)
            else
                BigReal((temp as BigDecimal).setScale(Utility.SCALE))
        } else {
            BigReal((this.getDec() * rhs.getDec()).setScale(Utility.SCALE))
        }
    }

    operator fun div(rhs: BigReal): BigReal {
        return if (this.exact != null && rhs.exact != null){
            val temp = this.exact as BigRealExact / rhs.exact as BigRealExact
            if (temp is BigRealExact)
                BigReal(temp)
            else
                BigReal((temp as BigDecimal).setScale(Utility.SCALE))
        } else {
            BigReal((this.getDec() / rhs.getDec()).setScale(Utility.SCALE))
        }
    }

    companion object {
        val ONE = BigReal(BigRealExact.ONE)
        val ZERO = BigReal(BigRealExact.ZERO)
        val PI = BigReal(BigRealExact.PI)
    }

    override fun toString(): String {
        return if (this.exact != null)
            this.exact.toString()
        else this.decimal!!.stripTrailingZeros().toPlainString()
    }

    override fun hashCode(): Int {
        return if (this.exact != null)
            this.exact!!.hashCode()
        else this.decimal!!.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as BigReal
        if (this.exact != null && other.exact != null)
            return this.exact == other.exact
        else if (this.decimal != null && other.decimal != null)
            return this.decimal!!.compareTo(other.decimal) == 0
        else
            return false
    }
}