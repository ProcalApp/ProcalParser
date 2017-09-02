package nodes

import calc.BigCmplxFrac
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class CombinationNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {

    private val left: Node = left!!
    private val right: Node = parser.expr(left, lexeme.lbp())

    override fun evaluate(): BigCmplxFrac {
        TODO("not implemented")
    }

    override fun toString(): String {
        return "(${left}C$right)"
    }

}