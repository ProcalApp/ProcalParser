package nodes

import calc.BigCmplx
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class ExponentialNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>) : Node {

    private val i: Int = Integer.parseInt(lexeme.text().substring(1))

    override fun evaluate(): BigCmplx {
        TODO("not implemented")
    }

    override fun toString(): String {
        return "E$i"
    }

}