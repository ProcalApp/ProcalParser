package nodes

import calc.BigCmplxExact
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class PermutationNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {

    private val left: Node = left!!
    private val right: Node = parser.expr(left, lexeme.lbp())

    override fun evaluate(): BigCmplxExact {
        TODO("not implemented")
    }

    override fun toString(): String {
        return "(${left}P$right)"
    }

}