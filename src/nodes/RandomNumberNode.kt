package nodes

import calc.type.BigCmplx
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class RandomNumberNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {

    override fun evaluate(): BigCmplx {
        TODO("not implemented")
    }

    override fun toString(): String {
        return "Ran#"
    }

}