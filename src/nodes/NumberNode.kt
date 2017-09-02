package nodes

import calc.BigCmplxFrac
import calc.BigFrac
import calc.BigFracPwr
import calc.BigPiFracPwr
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser
import java.math.BigDecimal

/**
 * Number node
 *
 * Node storing a number
 */

class NumberNode: Node {
    private var value: BigCmplxFrac = BigCmplxFrac()

    constructor(value: BigCmplxFrac) {
        this.value = value
    }

    constructor(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>) {
        this.value = BigCmplxFrac(BigPiFracPwr(BigFracPwr(BigFrac(BigDecimal(
                if (lexeme.text().equals(".")) "0" else lexeme.text()
        )))))
    }

    override fun evaluate(): BigCmplxFrac {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }

}