package nodes

import calc.BigCmplxFrac
import exceptions.BreakException
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class BreakNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {

    init {
        ProcalParserHelper.nextMustBeSeparator(parser, "break")
    }

    override fun evaluate(): BigCmplxFrac {
        throw BreakException()
    }

    override fun toString(): String {
        return "Break"
    }

}