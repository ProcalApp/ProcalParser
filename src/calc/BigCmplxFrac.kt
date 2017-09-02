package calc

/**
 * BigCmplxFrac Class
 *
 * A class using BigDecimal to store a complex unit,
 * in a form of
 * (a/b)^(m/n) + (c/d)^(p/q)i
 */

class BigCmplxFrac(real: BigFracPwr = BigFracPwr.ZERO, imag: BigFracPwr = BigFracPwr.ZERO) {
    private var real: BigFracPwr = real
    private var imag: BigFracPwr = imag

    fun conjugate(): BigCmplxFrac {
        return BigCmplxFrac(this.real, this.imag.negate())
    }

    fun isReal(): Boolean {
        return this.imag.isZero()
    }

    fun isRealInt(): Boolean {
        return isReal() && this.real.isInt()
    }

    override fun toString(): String {
        return if (!this.real.isZero()) this.real.toString() else {""} +
                if  (!this.imag.isZero()) " + " + this.imag.toString() + " i" else ""
    }

}
