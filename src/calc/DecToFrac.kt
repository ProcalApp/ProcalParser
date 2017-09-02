package calc

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.util.*

/**
 * DecToFrac Object
 *
 * Converts a BigDecimal into a BigFrac by either brute force / repeated inversion
 */

object DecToFrac {
    private val ACCURACY = 7
    private val BRUTE_THRESOLD = 13
    private val digits = ArrayList<Int>()
    private var digit = 0

    private fun inversion(dec: BigDecimal) {
        var decimal = dec
        if (decimal.compareTo(BigDecimal.ZERO) != 0) {
            decimal = BigDecimal.ONE.divide(decimal, MathContext(250, RoundingMode.HALF_UP))
            this.digits.add(decimal.toInt())
            if (decimal.setScale(ACCURACY, BigDecimal.ROUND_HALF_UP)
                    .compareTo(BigDecimal(decimal.toInt())) != 0) {
                decimal = decimal.subtract(BigDecimal(decimal.toInt()))
                inversion(decimal)
            }

        }
    }

    fun dectofrac(dec: BigDecimal): BigFrac {
        var decimal = dec
        var numerator: Long
        var denominator: Long
        if (decimal.scale() >= BRUTE_THRESOLD) { //By brute forcing
            this.digit = decimal.toInt()
            if (this.digit != 0) {
                this.digits.add(0)
                this.digits.add(digit)
                decimal = decimal.subtract(BigDecimal(digit))
            }
            inversion(decimal)
            denominator = digits.removeAt(digits.size - 1).toLong()
            numerator = 1
            while (digits.size != 0) {
                val temp = numerator + denominator * digits.removeAt(digits.size - 1)
                numerator = denominator
                denominator = temp
            }
            return BigFrac(numerator, denominator)

        } else { //By natural conversion
            val exp = decimal.scale()
            numerator = decimal.multiply(BigDecimal(Math.pow(10.0, exp.toDouble()))).toInt().toLong()
            denominator = Math.pow(10.0, exp.toDouble()).toInt().toLong()
            return BigFrac(numerator, denominator)
        }
    }
}