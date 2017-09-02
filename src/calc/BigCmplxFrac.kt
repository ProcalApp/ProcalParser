package calc

/**
 * BigCmplxFrac Class
 *
 * A class using BigDecimal to store a complex unit,
 * Form: (a/b) * (c/d) ^ (e/f) + (u/v) * (w/x) * (y/z) i
 * Domain: Imaginary
 */

class BigCmplxFrac(real: BigPiFracPwr = BigPiFracPwr.ZERO, imag: BigPiFracPwr = BigPiFracPwr.ZERO) {
    private var real: BigPiFracPwr = real
    private var imag: BigPiFracPwr = imag

    //TODO: Resolving complex case for negative base & fraction expn
    init {

    }

    fun conjugate(): BigCmplxFrac {
        return BigCmplxFrac(this.real, this.imag.negate())
    }

    fun isReal(): Boolean {
        return this.imag.isZero()
    }

    fun isRealInt(): Boolean {
        return isReal() && this.real.isInt()
    }

    fun evaluate() : BigCmplxDec{
        return BigCmplxDec(this.real.toDecimal(), this.imag.toDecimal())
    }

    operator fun plus(rhs: BigCmplxFrac): BigCmplxFrac {
        return ZERO
    }

    operator fun minus(rhs: BigCmplxFrac): BigCmplxFrac {
        return ZERO
    }

    operator fun times(rhs: BigCmplxFrac): BigCmplxFrac {
        return ZERO
    }

    operator fun div(rhs: BigCmplxFrac): BigCmplxFrac {
        return ZERO
    }


    override fun toString(): String {
        return if (!this.real.isZero()) this.real.toString() else {""} +
                if  (!this.imag.isZero()) " + " + this.imag.toString() + " i" else ""
    }

    override fun hashCode(): Int {
        return this.real.hashCode() * 31 + this.imag.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as BigCmplxFrac
        if (this.real == other.real && this.imag == other.imag) return true
        return false
    }

    companion object {
        val ONE = BigCmplxFrac(BigPiFracPwr.ONE)
        val ZERO = BigCmplxFrac()
        val PI = BigCmplxFrac(BigPiFracPwr.PI)
        val I = BigCmplxFrac(imag = BigPiFracPwr.ONE)
    }

}
