package calc

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

/**
 * This object contains necessary methods surrounding calculations involving BigDecimal values.
 */
object BigDecimalMath {

    private val TAYLOR_LEAST_TERMS = 4
    private val SCALE = 20
    private val PRECISION = MathContext(50, RoundingMode.HALF_UP)

    /**
     * This function continues to evaluates terms in the taylor series expansion until a certain precision is attained.
     * @param x The parameter of the function to be expanded
     * @param term A function that accepts a fixed x, an incrementing n and returns a BigDecimal and a notes map that may be used to store values that gets carried over to the next evaluation of the function
     * @param precision The MathContext to be used for comparing results to make sure of the return value's precision
     * @param start An optional parameter that specifies where to start counting up n
     */
    private fun taylor(x: BigDecimal, term: (x: BigDecimal, n: Int, notes: MutableMap<String, BigDecimal>) -> TaylorTerm, precision: MathContext, start: Int = 0): BigDecimal {

        var result = BigDecimal(0)
        var i = start
        var count = 0
        var newTerm = TaylorTerm(BigDecimal.ZERO)

        while (true) {
            newTerm = term(x, i++, newTerm.notes)
            val newResult = (result + newTerm.term).round(precision)
            count++
            if (count > TAYLOR_LEAST_TERMS && newResult.round(precision) == result.round(precision))
                return newResult
            result = newResult
        }

    }

    class TaylorTerm(val term: BigDecimal, val notes: MutableMap<String, BigDecimal> = HashMap<String, BigDecimal>())

    fun factorial(x: BigInteger): BigInteger {
        if (x < BigInteger.ZERO)
            throw IllegalArgumentException("Factorial cannot accept negative number")
        if (x > BigInteger.ZERO)
            return x * factorial(x - BigInteger.ONE)
        return BigInteger.ONE
    }

    fun isInt(x: BigDecimal): Boolean {
        return x.setScale(0, RoundingMode.HALF_UP) == x
    }

    fun factorial(x: BigDecimal): BigDecimal {
        if (!isInt(x))
            throw IllegalArgumentException("Factorial must have an integer as parameter")
        return BigDecimal(factorial(x.toBigInteger()))
    }

    fun sin(x: BigDecimal, scale: Int = SCALE): BigDecimal {
        return taylor(x, BigDecimalMath::sinTE, PRECISION, 0).setScale(scale, RoundingMode.HALF_UP).stripTrailingZeros()
    }

    private fun sinTE(x: BigDecimal, n: Int, notes: MutableMap<String, BigDecimal>): TaylorTerm {
        val xPowerCarry = notes["X"]

        notes["X2"] = notes["X2"] ?: x.pow(2)

        val newXPower = (if (xPowerCarry == null) x else xPowerCarry * notes["X2"]!!)

        notes["X"] = newXPower

        val term = ((-BigDecimal.ONE).pow(n).divide(factorial(BigDecimal(2*n+1)), PRECISION) * newXPower)

        return TaylorTerm(term, notes)
    }

    fun cos(x: BigDecimal, scale: Int = SCALE): BigDecimal {
        return taylor(x, BigDecimalMath::cosTE, PRECISION, 0).setScale(scale, RoundingMode.HALF_UP).stripTrailingZeros()
    }

    fun tan(x: BigDecimal): BigDecimal {
        return try {
            (sin(x, SCALE + 1)/cos(x, SCALE + 1)).setScale(SCALE, RoundingMode.HALF_UP).stripTrailingZeros()
        } catch (e: ArithmeticException) {
            throw ArithmeticException("tan of multiple of PI/2 is undefined")
        }
    }

    private fun cosTE(x: BigDecimal, n: Int, notes: MutableMap<String, BigDecimal>): TaylorTerm {
        val xPowerCarry = notes["X"]

        notes["X2"] = notes["X2"] ?: x.pow(2)

        val newXPower = (if (xPowerCarry == null) BigDecimal.ONE else xPowerCarry * notes["X2"]!!)

        notes["X"] = newXPower

        val term = ((-BigDecimal.ONE).pow(n).divide(factorial(BigDecimal(2*n)), PRECISION) * newXPower)

        return TaylorTerm(term, notes)
    }

    fun ln(x: BigDecimal): BigDecimal {
        if (x <= BigDecimal.ZERO)
            throw IllegalArgumentException("Ln cannot accept non-positive number")
        if (x - BigDecimal.ONE <= BigDecimal.ONE)
            return taylor(x - BigDecimal.ONE, BigDecimalMath::lnATE, PRECISION, 1).setScale(SCALE, RoundingMode.HALF_UP).stripTrailingZeros()
        return (ln(x - BigDecimal.ONE) - taylor(x - BigDecimal.ONE, BigDecimalMath::lnBTE, PRECISION, 1)).setScale(SCALE, RoundingMode.HALF_UP).stripTrailingZeros()
    }

    private fun lnATE(x: BigDecimal, n: Int, notes: MutableMap<String, BigDecimal>): TaylorTerm {
        val xPowerCarry = notes["X"]
        val newXPower = (if (xPowerCarry == null) x else xPowerCarry * x)

        notes["X"] = newXPower

        val term = -((-BigDecimal.ONE).pow(n).divide(BigDecimal(n), PRECISION) * newXPower)

        return TaylorTerm(term, notes)
    }

    private fun lnBTE(x: BigDecimal, n: Int, notes: MutableMap<String, BigDecimal>): TaylorTerm {
        val xPowerCarry = notes["X"]
        val newXPower = (if (xPowerCarry == null) x else xPowerCarry * x)

        notes["X"] = newXPower

        val term = ((-BigDecimal.ONE).pow(n).divide(BigDecimal(n) * newXPower, PRECISION))

        return TaylorTerm(term, notes)
    }
}