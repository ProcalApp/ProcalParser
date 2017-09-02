package nodes

import calc.BigCmplxFrac
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class ShorthandIfNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {

    private val ifNode = left!!
    private val thenNode = parser.expr(left, lexeme.lbp())

    override fun evaluate(): BigCmplxFrac {
        TODO("not implemented")
    }

    override fun toString(): String {
        return "$ifNode => ($thenNode)"
    }

}