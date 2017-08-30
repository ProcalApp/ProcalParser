package nodes

import calc.BigCmplx
import calc.BigFrac
import calc.BigFracPwr
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser
import java.math.BigDecimal

/**
 * Number node
 *
 * Node storing a number
 */

class NumberNode: Node {
    private var value: BigCmplx = BigCmplx()

    constructor(value: BigCmplx) {
        this.value = value
    }

    constructor(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>) {
        this.value = BigCmplx(BigFracPwr(BigFrac(BigDecimal(
                if (lexeme.text().equals(".")) "0" else lexeme.text()
        ))))
    }

    override fun evaluate(): BigCmplx{
        return value
    }

    override fun toString(): String {
        return value.toString()
    }

}