package calc.type

import calc.Utility
import calc.pow
import calc.setScale
import exceptions.UnfinishedException
import org.nevec.rjm.BigDecimalMath
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

/**
 * BigCmplxDecimal class
 *
 * A class storing a complex number with BigDeciaml real and imaginary parts
 * Form: a + bi
 * Domain: Complex
 */

class BigCmplxDecimal(real: BigDecimal = BigDecimal.ZERO, imag: BigDecimal = BigDecimal.ZERO) {

    private var real: BigDecimal = real.setScale(Utility.SCALE)
    private var imag: BigDecimal = imag.setScale(Utility.SCALE)

    constructor(a: String, b: String) : this(BigDecimal(a), BigDecimal(b))

    fun conjugate(): BigCmplxDecimal {
        return BigCmplxDecimal(this.real, this.imag.negate())
    }

    operator fun plus(rhs: BigCmplxDecimal): BigCmplxDecimal {
        return BigCmplxDecimal(this.real + rhs.real, this.imag + rhs.imag)
    }

    operator fun minus(rhs: BigCmplxDecimal): BigCmplxDecimal {
        return BigCmplxDecimal(this.real - rhs.real, this.imag - rhs.imag)
    }

    operator fun times(rhs: BigCmplxDecimal): BigCmplxDecimal {
        return BigCmplxDecimal(this.real * rhs.real - this.imag * rhs.imag, this.imag * rhs.real + this.real * rhs.imag)
    }

    operator fun div(rhs: BigCmplxDecimal): BigCmplxDecimal {
        return BigCmplxDecimal((this.real * rhs.real + this.imag * rhs.imag) / (rhs.real.pow(2) + rhs.imag.pow(2)),
                (this.imag * rhs.real - this.real * rhs.imag) / (rhs.real.pow(2) + rhs.imag.pow(2)))
    }

    fun isReal(): Boolean {
        return this.imag.compareTo(BigDecimal.ZERO) == 0
    }

    fun isPureImag(): Boolean {
        return this.real.compareTo(BigDecimal.ZERO) == 0
    }

    override fun toString(): String {
        return if (this.isReal()) {
            this.real.stripTrailingZeros().toPlainString()
        } else if (this.isPureImag()) {
            if (this.imag.compareTo(BigDecimal.ONE) == 0) {
                "i"
            } else {
                this.imag.stripTrailingZeros().toPlainString() + "i"
            }
        } else {
            if (this.imag.compareTo(BigDecimal.ONE) == 0) {
                this.real.stripTrailingZeros().toPlainString() + " + i"
            } else {
                this.real.stripTrailingZeros().toPlainString() + " + " + this.imag.stripTrailingZeros().toPlainString() + "i"
            }
        }
    }

    fun setScale(pair: Pair<Int, RoundingMode>): BigCmplxDecimal {
        return BigCmplxDecimal(this.real.setScale(pair), this.imag.setScale(pair))
    }

    fun pow(rhs: BigCmplxDecimal): BigCmplxDecimal {
        if(this.isReal() && rhs.isReal()) {
            val rhsFrac = BigFrac(rhs.real)
            // negative base & even denominator expn
            if (this.real.compareTo(BigDecimal.ZERO) <= 0 && rhsFrac.getDenom().mod(BigInteger.valueOf(2)) == BigInteger.ZERO) {
                throw UnfinishedException()
            } else
                return BigCmplxDecimal(this.real.pow(rhs.real))
        } else {
            val coeff: BigDecimal = BigDecimalMath.pow(this.real * this.real + this.imag * this.imag, rhs.real / BigDecimal(2)) *
                    BigDecimalMath.pow(Utility.E, -rhs.imag * BigDecimalMath.atan(this.imag / this.real))


            throw UnfinishedException()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as BigCmplxDecimal
        if (this.real == other.real && this.imag == other.imag) return true
        return false
    }

    override fun hashCode(): Int {
        return this.real.hashCode() * 31 + this.imag.hashCode()
    }

    companion object {
        val ONE = BigCmplxDecimal(BigDecimal.ONE)
        val ZERO = BigCmplxDecimal(BigDecimal.ZERO)
        val I = BigCmplxDecimal(imag = BigDecimal.ONE)
    }
}