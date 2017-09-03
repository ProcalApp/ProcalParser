package calc

import exceptions.ZeroPowerZeroException
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun main(args: Array<String>) {
    /* The followings are tests of Utility object */
    assertEquals(false, Utility.isInt(BigInteger("2312323442343242341")))
    assertEquals(true, Utility.isInt(BigInteger("2")))

    /* The followings are tests of BigFrac class */
    val frac1 = BigFrac(1, 2)
    val frac2 = BigFrac(1, 3)
    assertEquals("0.5", frac1.evaluate().toString())
    assertEquals("0.3333333333333333333333333", frac2.evaluate().toString())
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

    assertEquals("( 4507624986057943639 / 5340232221128654848 )", BigFrac(dec = BigDecimal("2.12345678912398732114")).toString())
    assertEquals("( 25 / 9 )", BigFrac(dec = BigDecimal("2.777777777777777777777")).toString())

    /** @test BigFracPwr */
    /** @rule No 0^0 */
    try {
        BigFracPwr(base = BigFrac.ZERO, expn = BigFrac.ZERO)
    } catch (e: ZeroPowerZeroException) {
        assertEquals("exceptions.ZeroPowerZeroException: Cannot have zero powered by zero.", e.toString())
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
    assertEquals("( 5 / 6 )", (BigFracPwr(base = frac1) + BigFracPwr(base = frac2)).toString())
    /** @rule addition of like terms -> change prop */
    assertEquals("1", (BigFracPwr(base = frac1) + BigFracPwr(base = frac1)).toString())
    /** @rule cast to BigDecimal if cannot be simplified */
    assertEquals("1.3710507951737255018850016", (BigFracPwr(base = frac1, expn = frac2) + BigFracPwr(base = frac2, expn = frac1)).toString())

    /** @fun minus() */
    /** @rule frac + frac -> frac */
    assertEquals("( 1 / 6 )", (BigFracPwr(base = frac1) - BigFracPwr(base = frac2)).toString())
    /** @rule addition of like terms -> change prop */
    assertEquals("0", (BigFracPwr(base = frac1) - BigFracPwr(base = frac1)).toString())
    /** @rule cast to BigDecimal if cannot be simplified */
    assertEquals("0.2163502567944739728667040", (BigFracPwr(base = frac1, expn = frac2) - BigFracPwr(base = frac2, expn = frac1)).toString())

    /** @fun times() */
    /** @rule rhs expn == 1 -> multiply with prop */
    assertEquals("( 1 / 6 ) * ( 1 / 2 ) ^ ( 1 / 3 )", (BigFracPwr(frac1, frac1, frac2) * BigFracPwr(frac2, BigFrac.ONE, BigFrac.ONE)).toString())
    /** @rule same expn -> indices law */
    assertEquals("( 1 / 6 ) ^ ( 1 / 3 )", (BigFracPwr(base = frac1, expn = frac2) * BigFracPwr(base = frac2, expn = frac2)).toString())
    /** @rule same base -> group base */
    assertEquals("( 1 / 2 ) ^ ( 5 / 6 )", (BigFracPwr(base = frac1, expn = frac2) * BigFracPwr(base = frac1, expn = frac1)).toString())
    /** @rule cast to BigDecimal if cannot be simplified */
    assertEquals("0.4582432123328675421278264", (BigFracPwr(base = frac1, expn = frac2) * BigFracPwr(base = frac2, expn = frac1)).toString())

    /** @fun div() */
    /** @rule rhs expn == 1 -> multiply with prop */
    assertEquals("( 3 / 2 ) * ( 1 / 2 ) ^ ( 1 / 3 )", (BigFracPwr(frac1, frac1, frac2) / BigFracPwr(frac2, BigFrac.ONE, BigFrac.ONE)).toString())
    /** @rule same expn -> indices law */
    assertEquals("( 3 / 2 ) ^ ( 1 / 3 )", (BigFracPwr(base = frac1, expn = frac2) / BigFracPwr(base = frac2, expn = frac2)).toString())
    /** @rule same base -> group base */
    assertEquals("2 ^ ( 1 / 6 )", (BigFracPwr(base = frac1, expn = frac2) / BigFracPwr(base = frac1, expn = frac1)).toString())
    /** @rule cast to BigDecimal if cannot be simplified */
    assertEquals("1.3747296369986026263834789", (BigFracPwr(base = frac1, expn = frac2) / BigFracPwr(base = frac2, expn = frac1)).toString())

    /* The followings are tests of BigRealExact class */
    val pfp1 = BigRealExact.PI
    val pfp2 = (BigRealExact.ONE + BigRealExact.ONE) as BigRealExact
    /** @fun toDecimal() */
    assertEquals("3.1415926535897932384626434", pfp1.toDecimal().toString())
    /** @fun negate() */
    assertEquals("-1 * pi", pfp1.negate().toString())
    /** @fun plus() */
    assertEquals("2 * pi", (pfp1 + pfp1).toString())
    assertEquals("5.1415926535897932384626434", (pfp1 + pfp2).toString())
    /** @fun minus() */
    assertEquals("0", (pfp1 - pfp1).toString())
    assertEquals("1.1415926535897932384626434", (pfp1 - pfp2).toString())
    /** @fun times() */
    assertEquals("9.8696044010893586188344911", (pfp1 * pfp1).toString())
    assertEquals("2 * pi", (pfp1 * pfp2).toString())
    /** @fun div() */
    assertEquals("pi", (pfp1 / pfp1).toString())
    assertEquals("( 1 / 2 ) * pi", (pfp1 / pfp2).toString())
    assertEquals("0.6366197723675813430755351", (pfp2 / pfp1).toString())

    /* The followings are tests of BigCmplxDecimal class */
    val cd1 = BigCmplxDecimal("2", "3")
    val cd2 = BigCmplxDecimal("3", "-2")
    assertEquals("5 + i", (cd1 + cd2).toString())
    assertEquals("-1 + 5i", (cd1 - cd2).toString())
    assertEquals("12 + 5i", (cd1 * cd2).toString())
    assertEquals("i", (cd1 / cd2).toString())

    /* The followings are tests of BigReal class */
    val r1 = BigReal.ONE
    val r2 = BigReal.PI
    val r3 = BigReal(BigDecimal("2.00909090"))
    assertEquals("4.1415926535897932384626434", (r1+r2).toString())
    assertEquals("5.1506835535897932384626434", (r2+r3).toString())
    assertEquals("2 * pi", (r2+r2).toString())
    assertEquals("-2.1415926535897932384626434", (r1-r2).toString())
    assertEquals("0", (r1-r1).toString())
    assertEquals("pi", (r1*r2).toString())
    assertEquals("6.3117452118341059282768268", (r2*r3).toString())
    assertEquals("1.5636886581835561738210269", (r2/r3).toString())
    assertEquals("2.0090909", (r3/r1).toString())
    
}