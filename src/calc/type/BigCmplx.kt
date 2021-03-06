package calc.type

import exceptions.ComplexNumberComparisonException
import exceptions.IllegalOperationException
import exceptions.NullException

/**
 * BigCmplx class
 * A container for either BigCmplxExact or BigCmplxDecimal
 */
/* TODO: Operators implementation */
/* TODO: Handling negative root multiplication */

class BigCmplx: BigValue {
    private var exact: BigCmplxExact? = null
    private var decimal: BigCmplxDecimal? = null

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

    //Operations

    override fun negate(): BigCmplx {
        TODO("not implemented")
    }

    override operator fun plus(rhs: BigCmplx): BigCmplx {
        TODO("not implemented")
    }

    override operator fun plus(rhs: BigReal): BigCmplx {
        TODO("not implemented")
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

    override fun compareTo(rhs: BigReal): Int {
        throw ComplexNumberComparisonException()
    }

    override fun compareTo(rhs: BigCmplx): Int {
        throw ComplexNumberComparisonException()
    }

    companion object {
        val ONE = BigCmplx(BigCmplxExact.ONE)
        val ZERO = BigCmplx(BigCmplxExact.ZERO)
        val PI = BigCmplx(BigCmplxExact.PI)
        val I = BigCmplx(BigCmplxExact.I)
    }

    /** Prefer exact value than decimal value */
    init {
        if (exact != null) decimal = null
    }
}