package nodes

import calc.BigCmplx
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class PowerNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {

    private val left: Node = left!!
    private val right: Node = parser.expr(left, lexeme.lbp())

    override fun evaluate(): BigCmplx {
        TODO("not implemented")
    }

    override fun toString(): String {
        return "($left)^($right)"
    }

}