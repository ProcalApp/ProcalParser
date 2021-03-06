package nodes

import calc.type.BigCmplx
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class TenPowerNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {

    private val right: Node = parser.expr(left, lexeme.lbp())

    override fun evaluate(): BigCmplx {
        TODO("not implemented")
    }

    override fun toString(): String {
        return "10^($right)"
    }

}