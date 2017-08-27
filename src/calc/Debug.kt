package calc

import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun main(args: Array<String>) {
    /* The followings are tests of BigFrac class */
    var frac1 = BigFrac(1, 2)
    var frac2 = BigFrac(1, 3)
    assertEquals(BigFrac(5, 6), frac1 + frac2)
    assertEquals(BigFrac(1, 6), frac1 - frac2)
    assertEquals(BigFrac(1, 6), frac1 * frac2)
    assertEquals(BigFrac(3, 2), frac1 / frac2)
    var frac3 = BigFrac(3, 6)
    assertEquals(BigFrac(1, 2), frac3)
    assertEquals(BigFrac(6, 12), frac3)
    assertEquals(BigFrac(1, 2), frac3.simplify())
    assertEquals(BigFrac(2, 1), frac3.inverse())
    assertEquals(true, BigFrac(1, -2).getNumer().compareTo(BigInteger.ZERO) < 0)
    assertEquals(BigFrac(-1, 2), frac3.negate())
    assertEquals(BigFrac(1, -2), frac3.negate())
    assertEquals(BigDecimal(0.5), frac3.toDecimal())
    var frac4 = BigFrac(5, 1)
    assertEquals(true, frac4.isInt())
    assertEquals(false, frac4.isZero())
    assertEquals(true, frac3.inverse().isInt())
    var frac5 = BigFrac(0, 1)
    assertEquals(true, frac5.isInt())
    assertEquals(true, frac5.isZero())
    assertTrue { BigFrac(1, 2) == BigFrac(2, 4) }
    assertTrue { BigFrac(0, 1) == BigFrac(0, 5) }
    assertTrue { BigFrac(-1, 2) == BigFrac(1, -2) }
    assertTrue { BigFrac(0, 4) == BigFrac(-0, 4) }
    assertTrue { BigFrac(1, -2).isNeg() }
    assertTrue { !BigFrac(0, -2).isNeg() }

    /* The followings are tests of BigFracPwr class */
    var fracpwr1 = BigFracPwr(BigFrac(1, 2), BigFrac(1, 2))
    var fracpwr2 = BigFracPwr(BigFrac(1, 3), BigFrac(1, 2))
    assertEquals(BigFracPwr(BigFrac(1, 2), BigFrac(-1, 2)), fracpwr1.inverse())
    assertEquals("0.70710678118654752440", fracpwr1.toDecimal().toString())
    assertEquals("1.28445705037617328891", (fracpwr1 + fracpwr2).toString())
    assertEquals("0.12975651199692175989", (fracpwr1 - fracpwr2).toString())
    assertEquals(BigFracPwr(2,1,1,1), BigFracPwr.ONE + BigFracPwr.ONE)
    assertEquals(BigFracPwr(BigFrac(1, 6), BigFrac(1, 2)), fracpwr1 * fracpwr2)
    assertEquals(BigFracPwr(BigFrac(3, 2), BigFrac(1, 2)), fracpwr1 / fracpwr2)
    assertTrue { BigFracPwr(BigFrac(1, 2), BigFrac(0, 1)) == BigFracPwr(BigFrac(5, 1), BigFrac(0, 1)) }
    assertTrue { BigFracPwr(BigFrac(0, 1), BigFrac(2, 5)) == BigFracPwr(BigFrac(0, 1), BigFrac(1, 1)) }

    var fracpwr3 = BigFracPwr(1, 2, 1, 2, false)
    assertEquals("-0.12975651199692175989", (fracpwr2 + fracpwr3).toString())
    assertEquals("-(( 1 / 2 ) ^ ( 3 / 4 ))", BigFracPwr(1,2,3,4,false).toString())
    assertEquals("-1", (BigFracPwr(2,1,1,1,false) + BigFracPwr.ONE).toString() )
    assertEquals("-1 / 8", BigFracPwr(2, -4, 3, 1).toString())
    assertEquals("-8", BigFracPwr(2, -4, -3, 1).toString())
    assertEquals("4", BigFracPwr(2, -4, -6, 3).toString())
    
//    assertTrue { BigFracPwr(1,4,1,2) == BigFracPwr(1,2,1,1)}

    /* The followings are tests of BigCmplx class */

}