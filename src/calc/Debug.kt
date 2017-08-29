package calc

import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun main(args: Array<String>) {
    /* The followings are tests of BigFrac class */
    val frac1 = BigFrac(1, 2)
    val frac2 = BigFrac(1, 3)
    assertEquals(BigFrac(5, 6), frac1 + frac2)
    assertEquals(BigFrac(1, 6), frac1 - frac2)
    assertEquals(BigFrac(1, 6), frac1 * frac2)
    assertEquals(BigFrac(3, 2), frac1 / frac2)
    val frac3 = BigFrac(3, 6)
    assertEquals(BigFrac(1, 2), frac3)
    assertEquals(BigFrac(6, 12), frac3)
//    assertEquals(BigFrac(1, 2), frac3.simplify())
    assertEquals(BigFrac(2, 1), frac3.inverse())
    assertEquals(true, BigFrac(1, -2).getNumer().compareTo(BigInteger.ZERO) < 0)
    assertEquals(BigFrac(-1, 2), frac3.negate())
    assertEquals(BigFrac(1, -2), frac3.negate())
    assertEquals(BigDecimal(0.5), frac3.toDecimal())
    val frac4 = BigFrac(5, 1)
    assertEquals(true, frac4.isInt())
    assertEquals(false, frac4.isZero())
    assertEquals(true, frac3.inverse().isInt())
    val frac5 = BigFrac(0, 1)
    assertEquals(true, frac5.isInt())
    assertEquals(true, frac5.isZero())
    assertTrue { BigFrac(1, 2) == BigFrac(2, 4) }
    assertTrue { BigFrac(0, 1) == BigFrac(0, 5) }
    assertTrue { BigFrac(-1, 2) == BigFrac(1, -2) }
    assertTrue { BigFrac(0, 4) == BigFrac(-0, 4) }
    assertTrue { BigFrac(1, -2).isNeg() }
    assertTrue { !BigFrac(0, -2).isNeg() }

    assertEquals("-( 1 / 2 )", BigFrac(2, -4).toString())

    /** @test BigFracPwr */
    /** @rule No 0^0 */
    try {
        BigFracPwr(base = BigFrac.ZERO, expn = BigFrac.ZERO)
    } catch (e: ArithmeticException) {
        assertEquals("java.lang.ArithmeticException: No 0^0", e.toString())
    }
    /** @rule Uniform 0 definition: 1 * 0 ^ 1 */
    assertEquals("0", BigFracPwr(BigFrac(5, 1)).toString())
    /** @rule Simplification of integer expn: 1 * (a / b) ^ 1 */
    assertEquals("( 2 / 3 )", BigFracPwr(base = BigFrac(2, 3)).toString())
    /** @rule Uniform integer definition: 1 * n ^ 1 */
    assertEquals("6", BigFracPwr(BigFrac(2, 1), BigFrac(3, 1)).toString())
    /** @rule Simplification of negative expn: to positive indices */
    assertEquals("2 ^ ( 2 / 3 )", BigFracPwr(BigFrac.ONE, BigFrac(1, 2), BigFrac(-2, 3)).toString())
    assertEquals("1.5874010519681994747517060", BigFracPwr(BigFrac.ONE, BigFrac(1, 2), BigFrac(-2, 3)).toDecimal().toString())
    /** @fun isZero() */
    assertEquals(true, BigFracPwr(BigFrac(5, 1)).isZero())
    assertEquals(false, BigFracPwr(base = BigFrac.ONE).isZero())
    /** @fun isInt() */
    assertEquals(true, BigFracPwr(BigFrac(5, 1)).isInt())
    assertEquals(true, BigFracPwr(BigFrac.ONE, expn = BigFrac(3, 4)).isInt())
    assertEquals(false, BigFracPwr(base = BigFrac(2, 1), expn = BigFrac(2, 3)).isInt())
    /** @fun isFrac() */
    assertEquals(true, BigFracPwr(base = BigFrac(2, 3)).isFrac())
    assertEquals(false, BigFracPwr(base = BigFrac(2, 3), expn = BigFrac(3, 5)).isFrac())
    /** @fun negate() */
    assertEquals(BigFracPwr(BigFrac(-1, 1), BigFrac.ONE, BigFrac.ONE), BigFracPwr.ONE.negate())
    /** @fun inverse() */
    assertEquals(BigFracPwr.ONE, BigFracPwr.ONE.inverse())
    assertEquals(BigFracPwr(-2, 1, 2, 3, 1, 2),
            BigFracPwr(-1, 2, 3, 2, 1, 2).inverse())
    /** @fun plus() */
    /** @rule frac + frac -> frac */
    println(BigFracPwr(base = frac1) + BigFracPwr(base = frac2))
    /** @rule addition of like terms -> change prop */
    println(BigFracPwr(base = frac1) + BigFracPwr(base = frac1))
    /** @rule cast to BigDecimal if cannot be simplified */
    println(BigFracPwr(base = frac1, expn = frac2) + BigFracPwr(base = frac2, expn = frac1))

    /** @fun minus() */
    /** @rule frac + frac -> frac */
    println(BigFracPwr(base = frac1) - BigFracPwr(base = frac2))
    /** @rule addition of like terms -> change prop */
    println(BigFracPwr(base = frac1) - BigFracPwr(base = frac1))
    /** @rule cast to BigDecimal if cannot be simplified */
    println(BigFracPwr(base = frac1, expn = frac2) - BigFracPwr(base = frac2, expn = frac1))

    /** @fun times() */
    /** @rule rhs expn == 1 -> multiply with prop */
    println(BigFracPwr(frac1, frac1, frac2) * BigFracPwr(frac2, BigFrac.ONE, BigFrac.ONE))
    /** @rule same expn -> indices law */
    println(BigFracPwr(base = frac1, expn = frac2) * BigFracPwr(base = frac2, expn = frac2))
    /** @rule same base -> group base */
    println(BigFracPwr(base = frac1, expn = frac2) * BigFracPwr(base = frac1, expn = frac1))
    /** @rule cast to BigDecimal if cannot be simplified */
    println(BigFracPwr(base = frac1, expn = frac2) * BigFracPwr(base = frac2, expn = frac1))

    /** @fun div() */
    /** @rule rhs expn == 1 -> multiply with prop */
    println(BigFracPwr(frac1, frac1, frac2) / BigFracPwr(frac2, BigFrac.ONE, BigFrac.ONE))
    /** @rule same expn -> indices law */
    println(BigFracPwr(base = frac1, expn = frac2) / BigFracPwr(base = frac2, expn = frac2))
    /** @rule same base -> group base */
    println(BigFracPwr(base = frac1, expn = frac2) / BigFracPwr(base = frac1, expn = frac1))
    /** @rule cast to BigDecimal if cannot be simplified */
    println(BigFracPwr(base = frac1, expn = frac2) / BigFracPwr(base = frac2, expn = frac1))

    /* The followings are tests of BigCmplx class */

}