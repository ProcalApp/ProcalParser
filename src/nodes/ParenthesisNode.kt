package nodes

import ProcalParserHelper.Tokens
import calc.BigCmplx
import org.bychan.core.basic.Lexeme
import org.bychan.core.basic.Parser

class ParenthesisNode(left: Node?, parser: Parser<Node>, lexeme: Lexeme<Node>): Node {

    private val right: Node

    override fun evaluate(): BigCmplx {
        return right.evaluate()
    }

    override fun toString(): String {
        return "($right)"
    }

    fun getContent(): Node {
        return right
    }

    init {
        var right = parser.expr(left, lexeme.lbp())
        val nextLexeme = parser.next()
        if (nextLexeme.token == Tokens.lparen.token)
            right = MultiplicationNode(right, parser.expr(right, nextLexeme.lbp()))
        if (!ProcalParserHelper.nextIsStatementEnd(parser))
            parser.swallow(Tokens.rparen.tokenName)
        this.right = right
    }
}