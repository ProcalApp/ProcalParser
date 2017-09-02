package nodes

import calc.*
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser
import java.math.BigDecimal

/**
 * Number node
 *
 * Node storing a number
 */

class NumberNode: Node {
    private var value: BigCmplx = BigCmplx(BigCmplxExact())

    constructor(value: BigCmplx) {
        this.value = value
    }

    constructor(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>) {
        this.value = BigCmplx(BigCmplxApprox(BigDecimal(
                if (lexeme.text() == ".") "0" else lexeme.text()
        )))
    }

    override fun evaluate(): BigCmplx {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }

}