package nodes

import calc.BigCmplxFrac
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class PercentNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {

    private val left: Node = left!!

    override fun evaluate(): BigCmplxFrac {
        TODO("not implemented")
    }

    override fun toString(): String {
        return "($left%)"
    }

}