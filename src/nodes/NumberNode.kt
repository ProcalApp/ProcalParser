package nodes

import calc.BigCmplx
import calc.BigFracPwr
import calc.BigFrac
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

    init {
        this.value = BigCmplx()
    }

    constructor(value: BigDecimal) {
        this.value = BigCmplx(BigFracPwr(BigFrac(value)))
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