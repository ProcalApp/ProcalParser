package calc

/**
 * BigCmplx Class
 *
 * A class using BigDecimal to store a complex unit,
 * in a form of
 * (a/b)^(m/n) + (c/d)^(p/q)i
 */

class BigCmplx(real: BigFracPwr = BigFracPwr(), imag: BigFracPwr = BigFracPwr()) {
    private var real: BigFracPwr = BigFracPwr.ZERO
    private var imag: BigFracPwr = BigFracPwr.ZERO

    init {
        this.real = real
        this.imag = imag
    }

    fun conjugate(): BigCmplx {
        return BigCmplx(this.real, this.imag.negate())
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
        } else if (!this.real.isZero()) {
            "(" + this.real.toString() + ") + "
        } else {
        }
        temp += if (this.imag.isInt()) {
            this.imag.toString() + "i"
        } else if (!this.imag.isZero()) {
            "(" + this.imag.toString() + ")i"
        } else {
        }
        return temp
    }

}
