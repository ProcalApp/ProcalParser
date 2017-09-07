package nodes

import calc.type.BigCmplx
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser
import ProcalParserHelper.Tokens

class WhileLoopNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {

    private val conditionNode: Node
    private val doNode: Node

    override fun evaluate(): BigCmplx {
        TODO("not implemented")
    }

    override fun toString(): String {
        return "While $conditionNode:" +
                ProcalParserHelper.indent(doNode.toString()) +
                "\nWhileEnd"
    }

    init {
        conditionNode = parser.expr(left, 3)

        val colon = parser.swallow(Tokens.colon.tokenName)
        doNode = parser.expr(left, colon.lbp())

        parser.swallow(Tokens.loopWhileEnd.tokenName)
        ProcalParserHelper.nextMustBeSeparator(parser, "WhileEnd")
    }
}