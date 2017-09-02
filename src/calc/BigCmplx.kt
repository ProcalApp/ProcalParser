package calc

import exceptions.NullException

/**
 * BigCmplx class
 * A container for either BigCmplxFrac or BigCmplxDec
 */

class BigCmplx {
    private var frac: BigCmplxFrac? = null
    private var dec: BigCmplxDec? = null

    constructor(frac: BigCmplxFrac) { this.frac = frac }
    constructor(dec: BigCmplxDec) { this.dec = dec }

    fun getVal() : Any {
        return this.frac ?: this.dec ?: throw NullException()
    }

    fun getDec() : BigCmplxDec {
        return this.frac?.evaluate() ?: this.dec ?: throw NullException()
    }

    operator fun plus(rhs: BigCmplx) : BigCmplx {
        return ZERO
    }

    operator fun minus(rhs: BigCmplx) : BigCmplx {
        return ZERO
    }
    operator fun times(rhs: BigCmplx) : BigCmplx {
        return ZERO
    }
    operator fun div(rhs: BigCmplx) : BigCmplx {
        return ZERO
    }

    companion object {
        val ONE = BigCmplx(BigCmplxFrac.ONE)
        val ZERO = BigCmplx(BigCmplxFrac.ZERO)
        val PI = BigCmplx(BigCmplxFrac.PI)
        val I = BigCmplx(BigCmplxFrac.I)
    }
}