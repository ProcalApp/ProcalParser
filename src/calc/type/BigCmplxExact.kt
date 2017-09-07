package calc.type

/**
 * BigCmplxExact Class
 *
 * A class using BigDecimal to store a complex unit,
 * Form: (a/b) * (c/d) ^ (e/f) + (u/v) * (w/x) * (y/z) i
 * Domain: Imaginary
 */
/* TODO: Resolving complex case from BigRealExact */
/* TODO: Operators implementation */

class BigCmplxExact(real: BigRealExact = BigRealExact.ZERO, imag: BigRealExact = BigRealExact.ZERO) {
    private var real: BigRealExact = real
    private var imag: BigRealExact = imag

    init {

    }

    fun conjugate(): BigCmplxExact {
        return BigCmplxExact(this.real, this.imag.negate())
    }

    fun isReal(): Boolean {
        return this.imag.isZero()
    }

    fun isRealInt(): Boolean {
        return isReal() && this.real.isInt()
    }

    fun evaluate(): BigCmplxDecimal {
        return BigCmplxDecimal(this.real.toDecimal(), this.imag.toDecimal())
    }

    operator fun plus(rhs: BigCmplxExact): BigCmplxExact {
        return ZERO
    }

    operator fun minus(rhs: BigCmplxExact): BigCmplxExact {
        return ZERO
    }

    operator fun times(rhs: BigCmplxExact): BigCmplxExact {
        return ZERO
    }

    operator fun div(rhs: BigCmplxExact): BigCmplxExact {
        return ZERO
    }


    override fun toString(): String {
        return if (!this.real.isZero()) this.real.toString() else {
            ""
        } +
                if (!this.imag.isZero()) " + " + this.imag.toString() + " i" else ""
    }

    override fun hashCode(): Int {
        return this.real.hashCode() * 31 + this.imag.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as BigCmplxExact
        if (this.real == other.real && this.imag == other.imag) return true
        return false
    }

    companion object {
        val ONE = BigCmplxExact(BigRealExact.ONE)
        val ZERO = BigCmplxExact()
        val PI = BigCmplxExact(BigRealExact.PI)
        val I = BigCmplxExact(imag = BigRealExact.ONE)
    }

}
