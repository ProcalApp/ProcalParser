package nodes

import calc.BigCmplx
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class AnswerNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>) : VariableNode("~Ans") {

    init {
        ProcalParserHelper.nextMustBeSeparator(parser, "break")
    }

    override fun toString(): String {
        return "Ans"
    }

}