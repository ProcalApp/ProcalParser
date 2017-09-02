package calc

import java.math.BigDecimal

/**
 * BigCmplxApprox class
 *
 * A class storing a complex number with BigDeciaml real and imaginary parts
 * Form: a + bi
 * Domain: Complex
 */

class BigCmplxApprox(real: BigDecimal = BigDecimal.ZERO, imag: BigDecimal = BigDecimal.ZERO) {

    private var real: BigDecimal = real.setScale(Utility.SCALE)
    private var imag: BigDecimal = imag.setScale(Utility.SCALE)

    constructor(a: String, b: String) : this(BigDecimal(a), BigDecimal(b))

    fun conjugate(): BigCmplxApprox {
        return BigCmplxApprox(this.real, this.imag.negate())
    }

    operator fun plus(rhs: BigCmplxApprox): BigCmplxApprox {
        return BigCmplxApprox(this.real + rhs.real, this.imag + rhs.imag)
    }

    operator fun minus(rhs: BigCmplxApprox): BigCmplxApprox {
        return BigCmplxApprox(this.real - rhs.real, this.imag - rhs.imag)
    }

    operator fun times(rhs: BigCmplxApprox): BigCmplxApprox {
        return BigCmplxApprox(this.real * rhs.real - this.imag * rhs.imag, this.imag * rhs.real + this.real * rhs.imag)
    }

    operator fun div(rhs: BigCmplxApprox): BigCmplxApprox {
        return BigCmplxApprox((this.real * rhs.real + this.imag * rhs.imag) / (rhs.real.pow(2) + rhs.imag.pow(2)),
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as BigCmplxApprox
        if (this.real == other.real && this.imag == other.imag) return true
        return false
    }

    override fun hashCode(): Int {
        return this.real.hashCode() * 31 + this.imag.hashCode()
    }

    companion object {
        val ONE = BigCmplxApprox(BigDecimal.ONE)
        val ZERO = BigCmplxApprox(BigDecimal.ZERO)
        val I = BigCmplxApprox(imag = BigDecimal.ONE)
    }
}