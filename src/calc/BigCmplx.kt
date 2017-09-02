package calc

import exceptions.NullException

/**
 * BigCmplx class
 * A container for either BigCmplxExact or BigCmplxApprox
 */
/* TODO: Operators implementation */

class BigCmplx {
    private var exact: BigCmplxExact? = null
    private var approx: BigCmplxApprox? = null

    /** Prefer exact value than approx value */
    init {
        if (exact != null) approx = null
    }

    constructor(exact: BigCmplxExact) {
        this.exact = exact; this.approx = null
    }

    constructor(approx: BigCmplxApprox) {
        this.approx = approx; this.exact = null
    }

    fun getVal(): Any {
        return this.exact ?: this.approx ?: throw NullException()
    }

    fun getDec(): BigCmplxApprox {
        return this.exact?.evaluate() ?: this.approx ?: throw NullException()
    }

    operator fun plus(rhs: BigCmplx): BigCmplx {
        return ZERO
    }

    operator fun minus(rhs: BigCmplx): BigCmplx {
        return ZERO
    }

    operator fun times(rhs: BigCmplx): BigCmplx {
        return ZERO
    }

    operator fun div(rhs: BigCmplx): BigCmplx {
        return ZERO
    }

    companion object {
        val ONE = BigCmplx(BigCmplxExact.ONE)
        val ZERO = BigCmplx(BigCmplxExact.ZERO)
        val PI = BigCmplx(BigCmplxExact.PI)
        val I = BigCmplx(BigCmplxExact.I)
    }
}