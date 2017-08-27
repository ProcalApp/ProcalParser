package calc

import org.nevec.rjm.BigDecimalMath
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun main(args: Array<String>) {
    /* The followings are tests of BigFrac class */
    var frac1 = BigFrac(1,2)
    var frac2 = BigFrac(1,3)
    assertEquals(BigFrac(5,6), frac1+frac2)
    assertEquals(BigFrac(1,6), frac1-frac2)
    assertEquals(BigFrac(1,6), frac1*frac2)
    assertEquals(BigFrac(3,2), frac1/frac2)
    var frac3 = BigFrac(3,6)
    assertEquals(BigFrac(1,2), frac3)
    assertEquals(BigFrac(6,12), frac3)
    assertEquals(BigFrac(1,2), frac3.simplify())
    assertEquals(BigFrac(2, 1), frac3.inverse())
    assertEquals(true, BigFrac(1, -2).getNumer().compareTo(BigInteger.ZERO) < 0)
    assertEquals(BigFrac(-1, 2), frac3.negate())
    assertEquals(BigFrac(1, -2), frac3.negate())
    assertEquals(BigDecimal(0.5), frac3.toDecimal())
    var frac4 = BigFrac(5,1)
    assertEquals(true, frac4.isInt())
    assertEquals(false, frac4.isZero())
    assertEquals(true, frac3.inverse().isInt())
    var frac5 = BigFrac(0 ,1)
    assertEquals(true, frac5.isInt())
    assertEquals(true, frac5.isZero())

    /* The followings are tests of BigFracPwr class */
    var fracpwr1 = BigFracPwr(BigFrac(1,2), BigFrac(1, 2))
    var fracpwr2 = BigFracPwr(BigFrac(1,3), BigFrac(1,2))
    assertEquals(BigFracPwr(BigFrac(1,2), BigFrac(-1, 2)), fracpwr1.inverse())
    println(fracpwr1.toDecimal())
    println(fracpwr1+fracpwr2)
    println(fracpwr1-fracpwr2)
    assertEquals(BigFracPwr(BigFrac(1,6), BigFrac(1,2)), fracpwr1*fracpwr2)
    assertEquals(BigFracPwr(BigFrac(3,2),BigFrac(1,2)),fracpwr1/fracpwr2)

    /* The followings are tests of BigCmplx class */

}