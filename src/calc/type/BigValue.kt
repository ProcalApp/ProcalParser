package calc.type

/**
 * Empty interface for BigReal and BigCmplx
 */

interface BigValue {

    fun negate(): BigValue

    operator fun plus(rhs: BigReal): BigValue

    operator fun plus(rhs: BigCmplx): BigValue

    operator fun compareTo(rhs: BigReal): Int

    operator fun compareTo(rhs: BigCmplx): Int
}