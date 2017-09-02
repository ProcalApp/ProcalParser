package nodes

import calc.BigCmplxFrac
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class NegationNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>) : Node {

    private val right: Node = parser.expr(left, lexeme.lbp())

    override fun evaluate(): BigCmplxFrac {
        TODO("not implemented")
    }

    override fun toString(): String {
        return "-($right)"
    }

}