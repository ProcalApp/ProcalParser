package calc.math

import calc.Utility
import calc.setScale
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

/**
 * This object contains necessary methods surrounding calculations involving BigDecimal values.
 */
object Functions {

    /**
     * A constant that specifies the least number of terms to be calculated by [taylor] until checking result precisions to halt evaluation
     */
    private val TAYLOR_LEAST_TERMS = 4
    /**
     * A constant that specifies the precision of rounding in [Functions]
     */
    private val PRECISION = MathContext(30, RoundingMode.HALF_UP)

    

    /**
     * This function continues to evaluates terms in the Taylor series expansion until a certain precision is attained.
     * @param x The parameter of the function to be expanded
     * @param term A function that accepts a fixed x, an incrementing n and returns a BigDecimal and a notes map that may be used to store values that gets carried over to the next evaluation of the function
     * @param precision The MathContext to be used for comparing results to make sure of the return value's precision
     * @param start An optional parameter that specifies where to start counting up n
     */
    private fun taylor(x: BigDecimal, term: (x: BigDecimal, n: Int, notes: MutableMap<String, BigDecimal>, precision: MathContext) -> TaylorTerm, precision: MathContext, start: Int = 0): BigDecimal {

        var result = BigDecimal(0)
        var i = start
        var count = 0
        var newTerm = TaylorTerm(BigDecimal.ZERO)

        while (true) {
            newTerm = term(x, i++, newTerm.notes, upPrecision(precision))
            val newResult = (result + newTerm.term).round(precision)
            count++
            if (count > TAYLOR_LEAST_TERMS && newResult.round(precision) == result.round(precision))
                return newResult.round(precision)
            result = newResult
        }

    }

    /**
     * A temporary value container for during Taylor series evaluation
     * @param term A term in the Taylor series represented in BigDecimal
     * @param notes A map for saving persistent values during Taylor series evaluation
     */
    class TaylorTerm(val term: BigDecimal, val notes: MutableMap<String, BigDecimal> = HashMap<String, BigDecimal>())

    /**
     * Factorial function which accepts a BigInteger.
     * @param x A BigInteger
     */
    fun factorial(x: BigInteger): BigInteger {
        if (x < BigInteger.ZERO)
            throw IllegalArgumentException("Factorial cannot accept negative number")
        if (x > BigInteger.ZERO)
            return x * factorial(x - BigInteger.ONE)
        return BigInteger.ONE
    }

    /**
     * Factorial function which accepts an integer represented in BigDecimal.
     * @param x A BigDecimal
     */
    fun factorial(x: BigDecimal): BigDecimal {
        if (!isInt(x))
            throw IllegalArgumentException("Factorial must have an integer as parameter")
        return BigDecimal(factorial(x.toBigInteger()))
    }
    
    private fun upPrecision(precision: MathContext, n: Int = 1): MathContext {
        return MathContext(precision.precision + n, RoundingMode.HALF_UP)
    }

    fun isInt(x: BigDecimal): Boolean {
        return x.setScale(0, RoundingMode.HALF_UP) == x
    }

    /**
     * Sine function. Evaluates solely with Taylor series expansion. Output result is scaled by [Utility.SCALE].
     * @param x A non-negative BigDecimal
     * @param precision A MathContext that specifies the number of precision places and rounding method
     */
    fun sin(x: BigDecimal, precision: MathContext = PRECISION): BigDecimal {
        return taylor(x, Functions::sinTE, precision, 0).setScale(Utility.SCALE).stripTrailingZeros()
    }

    private fun sinTE(x: BigDecimal, n: Int, notes: MutableMap<String, BigDecimal>, precision: MathContext): TaylorTerm {
        val xPowerCarry = notes["X"]

        notes["X2"] = notes["X2"] ?: x.pow(2)

        val newXPower = (if (xPowerCarry == null) x else xPowerCarry * notes["X2"]!!)

        notes["X"] = newXPower

        val term = ((-BigDecimal.ONE).pow(n).divide(factorial(BigDecimal(2 * n + 1)), precision) * newXPower)

        return TaylorTerm(term, notes)
    }

    /**
     * Cosine function. Evaluates solely with Taylor series expansion. Output result is scaled by [Utility.SCALE].
     * @param x A non-negative BigDecimal
     * @param precision A MathContext that specifies the number of precision places and rounding method
     */
    fun cos(x: BigDecimal, precision: MathContext = PRECISION): BigDecimal {
        return taylor(x, Functions::cosTE, precision, 0).setScale(Utility.SCALE).stripTrailingZeros()
    }

    private fun cosTE(x: BigDecimal, n: Int, notes: MutableMap<String, BigDecimal>, precision: MathContext): TaylorTerm {
        val xPowerCarry = notes["X"]

        notes["X2"] = notes["X2"] ?: x.pow(2)

        val newXPower = (if (xPowerCarry == null) BigDecimal.ONE else xPowerCarry * notes["X2"]!!)

        notes["X"] = newXPower

        val term = ((-BigDecimal.ONE).pow(n).divide(factorial(BigDecimal(2 * n)), precision) * newXPower)

        return TaylorTerm(term, notes)
    }

    /**
     * Tangent function. Underlying algorithm uses solely Taylor series expansion. Output result is scaled by [Utility.SCALE].
     * @param x A non-negative BigDecimal
     * @param precision A MathContext that specifies the number of precision places and rounding method
     */
    fun tan(x: BigDecimal, precision: MathContext = PRECISION): BigDecimal {
        val highPrecision = upPrecision(precision)
        return try {
            (sin(x, highPrecision) / cos(x, highPrecision)).setScale(Utility.SCALE).stripTrailingZeros()
        } catch (e: ArithmeticException) {
            throw ArithmeticException("tan of multiple of PI/2 is undefined")
        }
    }

    /**
     * Taylor function implementation of ln x
     * @param x A non-negative BigDecimal
     * @param precision A MathContext that specifies the number of precision places and rounding method
     */
    fun ln(x: BigDecimal, precision: MathContext = PRECISION): BigDecimal {
        if (x <= BigDecimal.ZERO)
            throw IllegalArgumentException("Ln cannot accept non-positive number")
        return (if (x <= Utility.TWO) {
            taylor(x - BigDecimal.ONE, Functions::lnTE, precision, 1)
        } else {
            var ln10Num = BigDecimal.ZERO
            var y = x
            while (y > Utility.TWO) {
                y = y.divide(BigDecimal.TEN)
                ln10Num += Utility.LN10
            }
            ln(y, upPrecision(precision)) + ln10Num
        }).round(precision).stripTrailingZeros()
    }

    private fun lnTE(x: BigDecimal, n: Int, notes: MutableMap<String, BigDecimal>, precision: MathContext): TaylorTerm {
        val xPowerCarry = notes["X"]
        val newXPower = (if (xPowerCarry == null) x else xPowerCarry * x)

        notes["X"] = newXPower

        val term = -((-BigDecimal.ONE).pow(n).divide(BigDecimal(n), precision) * newXPower)

        return TaylorTerm(term, notes)
    }

    fun log(x: BigDecimal, precision: MathContext = PRECISION): BigDecimal {
        return (ln(x, upPrecision(precision)).divide(Utility.LN10, precision)).round(precision).stripTrailingZeros()
    }

    /**
     * Taylor function implementation of e^x
     * @param x A BigDecimal
     * @param precision A MathContext that specifies the number of precision places and rounding method
     */
    fun ePow(x: BigDecimal, precision: MathContext = PRECISION): BigDecimal {
        return taylor(x, Functions::ePowTE, precision).stripTrailingZeros()
    }

    private fun ePowTE(x: BigDecimal, n: Int, notes: MutableMap<String, BigDecimal>, precision: MathContext): TaylorTerm {
        val k = notes["k"] ?: BigDecimal.ZERO
        val kFac = if (k == BigDecimal.ZERO) BigDecimal.ONE else (notes["kFac"] ?: BigDecimal.ONE) * k
        val xPowerCarry = notes["X"]
        val newXPower = if (xPowerCarry == null) BigDecimal.ONE else xPowerCarry * x

        notes["k"] = k + BigDecimal.ONE
        notes["kFac"] = kFac
        notes["X"] = newXPower

        return TaylorTerm(newXPower.divide(kFac, precision), notes)
    }

    /**
     * Power function for BigDecimal. Underlying algorithm uses Taylor series expansion which may slow down evaluation.
     * @param base A non-negative BigDecimal
     * @param power A non-negative BigDecimal
     * @param precision A MathContext that specifies the number of precision places and rounding method
     * @return base^power
     */
    fun pow(base: BigDecimal, power: BigDecimal, precision: MathContext = PRECISION): BigDecimal {
        if (base < BigDecimal.ZERO || power < BigDecimal.ZERO)
            throw IllegalArgumentException("This power function does not accepts negative arguments")
        if (base.compareTo(BigDecimal.ZERO) == 0 && power.compareTo(BigDecimal.ZERO) == 0)
            throw ArithmeticException("0 to power of 0 is undefined");

        //Use default power method if power is an integer
        if (isInt(power))
            return base.pow(power.toInt()).stripTrailingZeros()

        //Use custom solution if power if a decimal for a^b = e^(b*ln(a))
        return ePow(power * ln(base, upPrecision(precision, 2)), upPrecision(precision))
                .round(precision).stripTrailingZeros()
    }
}