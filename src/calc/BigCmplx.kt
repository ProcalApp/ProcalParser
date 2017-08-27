package calc

/**
 * BigCmplx Class
 *
 * A class using BigDecimal to store a complex unit,
 * in a form of
 * (a/b) + (c/d) i
 */

class BigCmplx(real: BigFrac = BigFrac(), imag: BigFrac = BigFrac()) {
    private var real: BigFrac = BigFrac()
    private var imag: BigFrac = BigFrac()

    init {
        this.real = real
        this.imag = imag
    }

    fun conjugate(): BigCmplx {
        return BigCmplx(this.real, this.imag.negate())
    }

    operator fun plus(rhs: BigCmplx): BigCmplx {
        return BigCmplx(this.real + rhs.real, this.imag + rhs.imag)
    }

    operator fun minus(rhs: BigCmplx): BigCmplx {
        return BigCmplx(this.real - rhs.real, this.imag - rhs.imag)
    }

    operator fun times(rhs: BigCmplx): BigCmplx {
        return BigCmplx(this.real * rhs.real, this.imag * rhs.imag)
    }

    operator fun div(rhs: BigCmplx): BigCmplx {
        return BigCmplx(this.real / rhs.real, this.imag / rhs.imag)
    }

    fun isReal(): Boolean {
        return this.imag.isZero()
    }

    fun isRealInt(): Boolean {
        return isReal() && this.real.isInt()
    }

    override fun toString(): String {
        var temp = ""
        temp += if (this.real.isInt()) {
            this.real.toString() + " + "
        } else if (!this.real.isZero()){
            "(" + this.real.toString() + ") + "
        } else {}
        temp += if (this.imag.isInt()) {
            this.imag.toString() + "i"
        } else if (!this.imag.isZero()){
            "(" + this.imag.toString() + ")i"
        } else {}
        return temp
    }

}
