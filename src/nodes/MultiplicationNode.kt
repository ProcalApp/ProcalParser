package nodes

import calc.BigCmplx
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class MultiplicationNode : Node {

    private val left: Node
    private val right: Node

    constructor(left: Node?, right: Node) {
        this.left = left!!
        this.right = right
    }

    constructor(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>) {
        this.left = left!!
        this.right = parser.expr(left, lexeme.lbp())
    }

    override fun evaluate(): BigCmplx {
        TODO("not implemented")
    }

    override fun toString(): String {
        return "($left)*($right)"
    }

}