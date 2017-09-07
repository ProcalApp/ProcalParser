package calc.type

import exceptions.NullException

/**
 * BigCmplx class
 * A container for either BigCmplxExact or BigCmplxDecimal
 */
/* TODO: Operators implementation */

class BigCmplx {
    private var exact: BigCmplxExact? = null
    private var decimal: BigCmplxDecimal? = null

    /** Prefer exact value than decimal value */
    init {
        if (exact != null) decimal = null
    }

    constructor(exact: BigCmplxExact) {
        this.exact = exact; this.decimal = null
    }

    constructor(decimal: BigCmplxDecimal) {
        this.decimal = decimal; this.exact = null
    }

    fun getVal(): Any {
        return this.exact ?: this.decimal ?: throw NullException()
    }

    fun getDec(): BigCmplxDecimal {
        return this.exact?.evaluate() ?: this.decimal ?: throw NullException()
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