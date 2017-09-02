package nodes

import calc.BigCmplxExact
import calc.BigFrac
import calc.BigFracPwr
import calc.BigRealExact
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser
import java.math.BigDecimal

/**
 * Number node
 *
 * Node storing a number
 */

class NumberNode: Node {
    private var value: BigCmplxExact = BigCmplxExact()

    constructor(value: BigCmplxExact) {
        this.value = value
    }

    constructor(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>) {
        this.value = BigCmplxExact(BigRealExact(BigFracPwr(BigFrac(BigDecimal(
                if (lexeme.text().equals(".")) "0" else lexeme.text()
        )))))
    }

    override fun evaluate(): BigCmplxExact {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }

}